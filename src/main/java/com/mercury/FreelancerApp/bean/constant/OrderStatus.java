package com.mercury.FreelancerApp.bean.constant;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderStatus {
    public static final int REJECTED = -1;
    public static final int PENDING = 0;
    public static final int IN_PROGRESS = 1;
    public static final int COMPLETED = 2;
    public static final Set<Integer> allStatus = new HashSet<>(List.of(REJECTED, PENDING, IN_PROGRESS, COMPLETED));

}
