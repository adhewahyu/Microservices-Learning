package com.dan.shared.model.transformer;

public interface MessageTransformer<I, O> {

    O transform(I input);
    
}
