package io.github.shuoros.escposspring.domain.model;

import io.github.shuoros.escposspring.service.PrinterService;
import lombok.Builder;
import lombok.Data;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.simple.Graphics2DRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

@Builder
public class TemplateData implements PrintData {

    private static final SpringTemplateEngine templateEngine;

    static {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/mail/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(1);
        resolver.setCacheable(true);
        templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(resolver);
    }

    private static final String TMP_PRINT_FILE = "tmp-print-file";
    private static final String XHTML = ".xhtml";

    private String template;
    public int width;
    private List<Variable> variables;

    @Override
    public void write(final PrinterService printerService) {
        try {
            final File tempFile = createTempFile();

            final OutputStream outputStream = Files.newOutputStream(tempFile.toPath());

            final Context generatedContext = generateContext(this.variables);

            renderTemplateToOutputStream(outputStream, generatedContext);

            final BufferedImage generateImage = generateImageOfHtml(tempFile);

            printerService.printImage(generateImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage generateImageOfHtml(File tempFile) throws MalformedURLException {
        final String tmpFileURL = tempFile.toURI().toURL().toExternalForm();
        return Graphics2DRenderer.renderToImageAutoSize(tmpFileURL, this.width, BufferedImage.TYPE_INT_ARGB);
    }

    private void renderTemplateToOutputStream(OutputStream outputStream, Context myContext) throws IOException {
        final String html = templateEngine.process(this.template, myContext);
        outputStream.write(html.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
    }

    private static Context generateContext(List<Variable> variables) {
        final Context myContext = new Context();
        variables.stream().peek(variable -> myContext.setVariable(variable.getKey(), variable.getValue())).close();
        return myContext;
    }

    private static File createTempFile() throws IOException {
        final File temp = File.createTempFile(TMP_PRINT_FILE, XHTML);
        temp.deleteOnExit();
        return temp;
    }

    @Data
    @Builder
    public static class Variable {

        private String key;
        private Object value;
    }
}
