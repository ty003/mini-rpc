package impl;

import com.javalearning.minirpc.CalculateService;

public class CalculateServiceImpl implements CalculateService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int minus(int a, int b) {
        return a - b;
    }
}
