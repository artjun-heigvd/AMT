package ch.heigvd.amt.builder;

import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.Set;

/**
 * This class is an annotation processor that generates a builder class for each class annotated with @GenerateBuilder.
 */
@SupportedAnnotationTypes("ch.heigvd.amt.builder.GenerateBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BuilderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // TODO: implement this method to generate a builder class for each class annotated with @GenerateBuilder
        return true;
    }
}
