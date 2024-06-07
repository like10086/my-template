package com.personal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.entry.MtUser;
import com.personal.mapper.MtUserMapper;
import com.personal.service.MtUserService;
import com.personal.utils.ThreadPoolUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * @author admin
 */
@Service
public class MtUserServiceImpl extends ServiceImpl<MtUserMapper, MtUser> implements MtUserService {

    @Resource
    private ThreadPoolUtil threadPoolUtil;

    @Override
    public Object test() {
        ExecutorService defaultThreadPool = threadPoolUtil.createDefaultThreadPool();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            defaultThreadPool.execute(() -> {
                System.out.println("输出:" + finalI);
            });
        }
        defaultThreadPool.shutdown();

        return null;
    }
}
