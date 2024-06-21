package com.example.capstone_backend.domain.home.service;


import com.example.capstone_backend.domain.community.exception.TooManyContentsException;
import com.example.capstone_backend.domain.user.CompetitorRepository;
import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.dto.request.UserCompetitorAddDTO;
import com.example.capstone_backend.domain.user.dto.request.UserCompetitorDeleteDTO;
import com.example.capstone_backend.domain.user.entity.Competitor;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import com.example.capstone_backend.domain.user.exception.AlreadyExistCompetitorException;
import com.example.capstone_backend.domain.user.exception.TooManyCompetitorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HomeWriteService {

    final private CompetitorRepository competitorRepository;
    final private UserInfoRepository UserInfoRepository;

    public void addCompetitor(
            UserCompetitorAddDTO userCompetitorAddDTO,
            UserInfo userInfo
    ){
        Competitor isExist = competitorRepository.findByFromUserIdAndToUserId(
                userInfo,
                UserInfoRepository.findById(userCompetitorAddDTO.competitorId()).orElseThrow()
        );
        if(isExist != null){
            throw new AlreadyExistCompetitorException();
        }
        else if(competitorRepository.findAllByFromUserId(userInfo).size() >= 3){
            throw new TooManyCompetitorException();
        }
        else{
            competitorRepository.save(
                    Competitor.builder()
                            .fromUserId(userInfo)
                            .toUserId(
                                    UserInfoRepository.findById(userCompetitorAddDTO.competitorId()).orElseThrow())
                            .build());
        }
    }

    public void deleteCompetitor(
            UserCompetitorDeleteDTO userCompetitorDeleteDTO,
            UserInfo userInfo
    ){
        competitorRepository.deleteByFromUserIdAndToUserId(
                userInfo,
                UserInfoRepository.findById(userCompetitorDeleteDTO.competitorId()).orElseThrow()
        );
    }
}
