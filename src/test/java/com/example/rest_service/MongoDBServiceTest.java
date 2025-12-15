package com.example.rest_service;

import com.example.rest_service.MongoDB.EntityToSave;
import com.example.rest_service.MongoDB.MongoDBService;
import jakarta.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class MongoDBServiceTest extends MongoDBService{

}
