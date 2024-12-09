package org.example.services;

import org.example.modules.Feedback;
import org.example.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service
public class FeedbackService {
    public FeedbackRepository feedbackRepository;
    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> getAllFeedback(){
        return feedbackRepository.findAll();
    }
    public Feedback createFeedback(Feedback feedback){
        return feedbackRepository.save(feedback);
    }
    public Feedback getFeedbackById(long feedbackId) {
        return feedbackRepository.findById(feedbackId).orElseThrow(() -> new EntityNotFoundException("Producer not found with id: " + feedbackId));
    }
    public void deleteFeedbackById(long feedbackId) {feedbackRepository.deleteById(feedbackId); }
}
