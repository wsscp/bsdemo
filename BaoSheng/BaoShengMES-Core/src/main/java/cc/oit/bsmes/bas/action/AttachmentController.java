package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.service.AttachmentService;
import cc.oit.bsmes.bas.service.DataDicService;
import com.alibaba.fastjson.JSONObject;
import jxl.write.WriteException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by jijy on 2014/11/13.  
 */
@Controller
@RequestMapping("/bas/attachment")
public class AttachmentController {

	
    @Resource
    private AttachmentService attachmentService;

    @RequestMapping(value="/download/{refId}", method = RequestMethod.GET)
    @ResponseBody
    public void download(HttpServletResponse response,
                                 @PathVariable String refId) throws IOException {
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        attachmentService.downLoadOne(outputStream, refId);
        outputStream.close();
    }

}
