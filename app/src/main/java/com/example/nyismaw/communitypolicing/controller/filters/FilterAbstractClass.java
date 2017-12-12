package com.example.nyismaw.communitypolicing.controller.filters;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by nyismaw on 12/11/2017.
 */

public abstract class FilterAbstractClass extends Thread implements FilterPipeInterface{
    ObjectInputStream pis ;
    ObjectOutputStream pos ;


}
