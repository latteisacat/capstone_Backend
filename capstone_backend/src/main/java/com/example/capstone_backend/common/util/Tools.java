package com.example.capstone_backend.common.util;

import com.example.capstone_backend.domain.user.ExerciseRepository;
import com.example.capstone_backend.domain.user.entity.Exercise;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Tools {

    public static Double parsingRecord(String record){
        //TODO: kg 외에 달리기 같은 운동을 위해 km/h같은 단위 추가 할 것
        int index = record.indexOf("kg");
        if (index == -1){
            throw new IllegalArgumentException("record must contain 'kg' string");
        }
        else{
            return Double.parseDouble(record.substring(0, index));
        }
    }

}
