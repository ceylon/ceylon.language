doc "An annotation that may occur at most once
     at a single program element."
shared interface OptionalAnnotation<out AnnotationValue, in ProgramElement>
        satisfies ConstrainedAnnotation<AnnotationValue,AnnotationValue?,ProgramElement>
        given AnnotationValue satisfies Annotation<AnnotationValue>
        given ProgramElement satisfies Annotated {}
