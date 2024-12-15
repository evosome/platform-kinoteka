package org.example.services;

import org.example.modules.Cinemas;
import org.example.modules.Feedback;
import org.example.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;
import java.util.List;
@Service
public class FeedbackService {
    public FeedbackRepository feedbackRepository;
    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }
    public Page<Feedback> getAllFeedback(int page, int size){
        return feedbackRepository.findAll(PageRequest.of(page, size));
    }
    public Feedback createFeedback(Feedback feedback){
        return feedbackRepository.save(feedback);
    }
    public Feedback getFeedbackById(long feedbackId) {
        return feedbackRepository.findById(feedbackId).orElseThrow(() -> new EntityNotFoundException("Producer not found with id: " + feedbackId));
    }
    public void deleteFeedbackById(long feedbackId) {feedbackRepository.deleteById(feedbackId); }
}
