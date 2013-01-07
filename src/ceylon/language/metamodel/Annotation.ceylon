doc "An annotation."
shared interface Annotation<out AnnotationValue> 
        given AnnotationValue satisfies Annotation<AnnotationValue> {}
