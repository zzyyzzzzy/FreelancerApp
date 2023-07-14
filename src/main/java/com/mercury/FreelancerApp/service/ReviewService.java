package com.mercury.FreelancerApp.service;

import com.mercury.FreelancerApp.bean.Order;
import com.mercury.FreelancerApp.bean.Review;
import com.mercury.FreelancerApp.bean.constant.OrderStatus;
import com.mercury.FreelancerApp.dao.OrderDao;
import com.mercury.FreelancerApp.dao.ReviewDao;
import com.mercury.FreelancerApp.exception.ForbiddenOperationException;
import com.mercury.FreelancerApp.exception.InvalidInputException;
import com.mercury.FreelancerApp.exception.ReviewNotFoundException;
import com.mercury.FreelancerApp.http.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDao reviewDao;
    private final OrderDao orderDao;
    public Response save(Review review){
        try {
            // throw exception if order does not exist
            Order existedOrder = orderDao.findById(review.getOrderId()).orElseThrow(ForbiddenOperationException::new);
            if(existedOrder.getOrderStatus() != OrderStatus.COMPLETED) throw new ForbiddenOperationException();
            Review exist = reviewDao.findByOrderId(review.getOrderId()).orElse(null);
            if(exist != null){
                review.setId(exist.getId());
                return update(review);
            }
            reviewDao.save(review);
            return new Response(true, "Review Created!");
        }catch (Exception e){
            e.printStackTrace();
            throw new InvalidInputException();
        }
    }

    public Response update(Review review){
        Review oldReview = reviewDao.findById(review.getId()).orElseThrow(ReviewNotFoundException::new);
        oldReview.setComment(review.getComment());
        oldReview.setRating(review.getRating());
        reviewDao.save(oldReview);
        return new Response(true, "Review Updated!");
    }
}
