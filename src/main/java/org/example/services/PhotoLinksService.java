package org.example.services;
import org.example.modules.PhotoLinks;
import org.example.repositories.PhotoLinksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;
import java.util.List;
@Service
public class PhotoLinksService {
    public PhotoLinksRepository photoLinksRepository;
    @Autowired
    public PhotoLinksService(PhotoLinksRepository photoLinksRepository) {
        this.photoLinksRepository = photoLinksRepository;
    }
    public Page<PhotoLinks> getAllLinks(int page, int size) {
        return photoLinksRepository.findAll(PageRequest.of(page, size));
    }
    public PhotoLinks createLink(PhotoLinks photoLinks) {
        return photoLinksRepository.save(photoLinks);
    }
    public PhotoLinks getLinkById(long id) {
        return photoLinksRepository.getReferenceById(id);
    }
    public void deleteLinkById(long id) {photoLinksRepository.deleteById(id);}
}
