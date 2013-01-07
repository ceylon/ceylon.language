doc "An annotation. This interface encodes
     constraints upon the annotation in its
     type arguments."
shared interface ConstrainedAnnotation<out AnnotationValue, out Values, in ProgramElement>
        //of OptionalAnnotation<AnnotationValue,ProgramElement> | SequencedAnnotation<AnnotationValue,ProgramElement>
        satisfies Annotation<AnnotationValue>
        given AnnotationValue satisfies Annotation<AnnotationValue>
        given ProgramElement satisfies Annotated {
    shared Boolean occurs(Annotated programElement) {
        throw;
        //return programElement is ProgramElement;
    }
}
