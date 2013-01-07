doc "An annotation that may occur multiple times
     at a single program element."
shared interface SequencedAnnotation<out AnnotationValue, in ProgramElement>
        satisfies ConstrainedAnnotation<AnnotationValue,AnnotationValue[],ProgramElement>
        given AnnotationValue satisfies Annotation<AnnotationValue>
        given ProgramElement satisfies Annotated {}
