package com.example.nyismaw.communitypolicing.controller.filters;

import com.example.nyismaw.communitypolicing.model.Issues;

import java.util.List;

/**
 * Created by nyismaw on 12/9/2017.
 */

 public interface FilterChainInterface {

    public List<Issues> filter(List<Issues> issues);

 }
