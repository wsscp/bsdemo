package cc.oit.bsmes.bas;

import cc.oit.bsmes.bas.service.AttachmentService;
import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * Created by jijy on 2014/11/14.
 */
public class AttachmentServiceTest extends BaseTest {

    @Resource
    private AttachmentService attachmentService;

    @Test
    @Rollback(false)
    public void testUpload() throws IOException {
        attachmentService.upload(new File("E:\\我的酷盘\\图片\\One Piece\\01.png"), InterfaceDataType.ALARM, "rest", "myid");
    }
}
