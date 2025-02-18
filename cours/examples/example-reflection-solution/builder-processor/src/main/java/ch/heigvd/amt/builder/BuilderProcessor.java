package ch.heigvd.amt.builder;

import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.Set;

/**
 * This class is an annotation processor that generates a builder class for each class annotated with @GenerateBuilder.
 */
@SupportedAnnotationTypes("ch.heigvd.amt.builder.GenerateBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BuilderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(GenerateBuilder.class)) {
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                // Error: @GenerateBuilder is only applicable to classes
                continue;
            }

            // Get the class name and package name
            TypeElement typeElement = (TypeElement) annotatedElement;
            String className = typeElement.getSimpleName().toString();
            String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();

            // Create a new builder class
            TypeSpec.Builder builderClass = TypeSpec.classBuilder(className + "Builder")
                    .addModifiers(Modifier.PUBLIC);

            // Initialize the fields in the build() method
            MethodSpec.Builder buildMethodBuilder = MethodSpec.methodBuilder("build")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.get(typeElement.asType()))
                    .addStatement("$T result = new $T()", TypeName.get(typeElement.asType()), TypeName.get(typeElement.asType()))
                    .addStatement("Class<?> clazz = result.getClass()", TypeName.get(typeElement.asType()), TypeName.get(typeElement.asType()));

            // Generate the fields and methods for the builder class
            for (Element enclosedElement : typeElement.getEnclosedElements()) {
                if (enclosedElement.getKind() == ElementKind.FIELD) {
                    // Get the field name and type
                    VariableElement variableElement = (VariableElement) enclosedElement;
                    String fieldName = variableElement.getSimpleName().toString();
                    TypeMirror fieldType = variableElement.asType();

                    // Add the field in builder class
                    FieldSpec fieldSpec = FieldSpec.builder(TypeName.get(fieldType), fieldName)
                            .addModifiers(Modifier.PRIVATE)
                            .build();
                    builderClass.addField(fieldSpec);

                    // Add the setter method in builder class
                    MethodSpec setterMethod = MethodSpec.methodBuilder("set" + capitalize(fieldName))
                            .addModifiers(Modifier.PUBLIC)
                            .returns(ClassName.get(packageName, className + "Builder"))
                            .addParameter(TypeName.get(fieldType), fieldName)
                            .addStatement("this.$N = $N", fieldName, fieldName)
                            .addStatement("return this")
                            .build();
                    builderClass.addMethod(setterMethod);

                    // Add field initialization statements to the build() method
                    buildMethodBuilder
                            .beginControlFlow("try")
                            .addStatement("$T $NField = clazz.getDeclaredField(\"$N\")",  ClassName.get("java.lang.reflect", "Field"), fieldName, fieldName)
                            .addStatement("$NField.setAccessible(true)", fieldName)
                            .addStatement("$NField.set(result, this.$N)", fieldName, fieldName)
                            .nextControlFlow("catch (Exception e)")
                            .addStatement("e.printStackTrace()")
                            .endControlFlow();
                }
            }

            // Finalize the build() method
            buildMethodBuilder.addStatement("return result");
            builderClass.addMethod(buildMethodBuilder.build());

            // Write the builder class to a file
            try {
                JavaFile.builder(packageName, builderClass.build())
                        .build()
                        .writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                // Do nothing
            }
        }

        return true;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
