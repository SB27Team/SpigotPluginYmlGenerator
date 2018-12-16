package team.sb27.spigotymlgenerator;


import team.sb27.spigotymlgenerator.util.StringUtil;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes("team.sb27.spigotymlgenerator.PluginYml")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class PluginYmlProcessor extends AbstractProcessor {

    private ProcessingEnvironment pEnviremnt;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        pEnviremnt = processingEnvironment;
        super.init(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment) {
        for (Element element : environment.getElementsAnnotatedWith(PluginYml.class)) {
            if (!(element instanceof TypeElement)) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "@GenerateYaml is not a TypeElement");
                return false;
            }
            TypeElement mainTypeElement = (TypeElement) element;
            try {
                FileObject descriptionFile = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "plugin.yml");
                try (Writer writer = descriptionFile.openWriter()) {
                    writer.append("# plugin.yml generated with DavidLib\n");
                    PluginYml annotation = mainTypeElement.getAnnotation(PluginYml.class);
                    String main = String.valueOf(pEnviremnt.getElementUtils().getBinaryName(mainTypeElement));
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Building plugin.yml for " + main);
                    writer.append("main: ").append(main).append("\n");
                    writer.append("name: ").append(annotation.name()).append("\n");
                    writer.append("version: ").append(String.valueOf(annotation.version())).append("\n");
                    writer.append("author: ").append(annotation.author()).append("\n");
                    writer.append("depend: ").append(StringUtil.join(annotation.dependencys(), obj -> " + obj + ", ", ")).append("\n");
                    writer.append("softdepend: ").append(StringUtil.join(annotation.softDependencys(), obj -> " + obj + ", ", ")).append("\n");
                }
            } catch (IOException ex) {
                throw new RuntimeException("Failed to create plugin.yml", ex);
            }
        }
        return true;
    }
}
