/*
 * Do we really need to enforce that you can't 
 * ask for the annotations of a certain type for a
 * certain program element unless the annotation 
 * type can appear at that program element? Why not
 * just return no annotations?
 * 
 */

shared Values annotations<AnnotationValue,Values,ProgramElement>(
              Type<ConstrainedAnnotation<AnnotationValue,Values,ProgramElement>> annotationType,
              ProgramElement programElement)
           given AnnotationValue satisfies ConstrainedAnnotation<AnnotationValue,Values,ProgramElement>
           //given Values of (Object?) | (Object[])
           given ProgramElement satisfies Annotated { throw; }

shared AnnotationValue? optionalAnnotation<AnnotationValue,ProgramElement>(
            Type<OptionalAnnotation<AnnotationValue,ProgramElement>> annotationType,
            ProgramElement programElement)
        given AnnotationValue satisfies OptionalAnnotation<AnnotationValue,ProgramElement>
        given ProgramElement satisfies Annotated { 
    return annotations<AnnotationValue,AnnotationValue?,ProgramElement>(annotationType, programElement); 
}

shared AnnotationValue[] sequencedAnnotations<AnnotationValue,ProgramElement>(
            Type<SequencedAnnotation<AnnotationValue,ProgramElement>> annotationType,
            ProgramElement programElement)
        given AnnotationValue satisfies SequencedAnnotation<AnnotationValue,ProgramElement>
        given ProgramElement satisfies Annotated { 
    return annotations<AnnotationValue,AnnotationValue[],ProgramElement>(annotationType, programElement); 
}