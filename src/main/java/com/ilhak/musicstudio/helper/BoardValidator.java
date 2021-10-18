package com.ilhak.musicstudio.helper;

import com.ilhak.musicstudio.model.Board;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BoardValidator implements Validator{

    

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj;
        if(ObjectUtils.isEmpty(b.getContent())) {
            errors.rejectValue("content", "key", "내용을 입력하세요.");
        }
    }
    
}
