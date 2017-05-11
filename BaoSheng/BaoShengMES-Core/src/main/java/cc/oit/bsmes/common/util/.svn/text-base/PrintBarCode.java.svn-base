package cc.oit.bsmes.common.util;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by joker on 2014/8/20 0020.
 */
@org.springframework.stereotype.Component
public class PrintBarCode implements Printable {


    private static final int FONT_SIZE = 9;

    private static final Font defaultFont = new Font("新宋体", Font.PLAIN, FONT_SIZE);

    private static final String PRODUCT_CODE_TITLE = "产品代码:";

    private static final String WORK_ORDER_NO_TITLE = "生产单号:";

    private static final String PRINT_TIME_TITLE = "打印时间:";

    private static final String ORDER_LENGTH_TITLE = "长度:";

    private static final int OFFSET = 5;

    private static final int CANVAS_WIDTH = 280;

    private static final int CANVAS_HEIGHT = 140;

    private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private String productCode;
    private String barCodeText;
    private String workOrderNo;
    private double reportLength;


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Component c = null;
        //print string
        //转换成Graphics2D
        Graphics2D g2 = (Graphics2D) graphics;
        //设置打印颜色为黑色
        g2.setColor(Color.black);

        //打印起点坐标
        double x = pageFormat.getImageableX();
        double y = pageFormat.getImageableY();

        switch(pageIndex){
            case 0:
                //设置打印字体（字体名称、样式和点大小）（字体名称可以是物理或者逻辑名称）
                g2.setFont(defaultFont);
                //1.线宽 2、3、不知道，4、空白的宽度，5、虚线的宽度，6、偏移量
                g2.setStroke(new   BasicStroke(0.5f,   BasicStroke.CAP_BUTT,   BasicStroke.JOIN_MITER,   2.0f));
                drawTable(g2);

                String file = "you target dir";//WebContextUtils.getPropValue(WebConstants.BARCODE_PND_FILE_PATH)+barCodeText+".jpg";

                Image src = Toolkit.getDefaultToolkit().getImage(file);
                int img_Height=src.getHeight(c);
                int img_width = src.getWidth(c);
                int leftOffset = (CANVAS_WIDTH - OFFSET*2 - img_width) / 2 ;

                g2.drawImage(src,OFFSET+leftOffset,OFFSET+5,c);
                //画条码下面的那一条线
                g2.drawLine(OFFSET,OFFSET+5+img_Height,CANVAS_WIDTH-OFFSET,OFFSET+5+img_Height);

                int top = OFFSET +5  + img_Height;

                int trHeight = (CANVAS_HEIGHT -top) / 3;

                g2.drawLine(OFFSET,top+trHeight,CANVAS_WIDTH-OFFSET,top+trHeight);
                g2.drawLine(OFFSET,top+trHeight*2,CANVAS_WIDTH-OFFSET,top+trHeight*2);


                int trTopOffSet = (trHeight - FONT_SIZE)/2 +5;

                g2.drawString(PRODUCT_CODE_TITLE,OFFSET *2,top+trTopOffSet);
                g2.drawString(WORK_ORDER_NO_TITLE,OFFSET *2,top+trHeight+ trTopOffSet);
                g2.drawString(PRINT_TIME_TITLE, OFFSET * 2, top + trHeight * 2 + trTopOffSet);

                g2.drawString(productCode,60,top+trTopOffSet);
                g2.drawString(workOrderNo,60,top+trHeight+ trTopOffSet);
                g2.drawString(reportLength+"m",205,top + trHeight * 2 + trTopOffSet);

                g2.drawLine(55,top,55,CANVAS_HEIGHT - OFFSET);

                g2.drawString(DF.format(new Date()),60,top + trHeight * 2 + trTopOffSet);

                g2.drawLine(160,top + trHeight * 2,160,CANVAS_HEIGHT - OFFSET);
                g2.drawString(ORDER_LENGTH_TITLE,160+OFFSET,top + trHeight * 2 + trTopOffSet);
                g2.drawLine(200,top + trHeight * 2,200,CANVAS_HEIGHT - OFFSET);

                return PAGE_EXISTS;
            default:
                return NO_SUCH_PAGE;
        }

    }

    private static void drawTable(Graphics2D g2){
        g2.drawLine(OFFSET,OFFSET,CANVAS_WIDTH - OFFSET,OFFSET);      //上边框
        g2.drawLine(OFFSET,OFFSET,OFFSET,CANVAS_HEIGHT-OFFSET);      //左边框
        g2.drawLine(OFFSET,CANVAS_HEIGHT-OFFSET ,CANVAS_WIDTH - OFFSET,CANVAS_HEIGHT-OFFSET );    //下边框
        g2.drawLine(CANVAS_WIDTH - OFFSET,OFFSET,CANVAS_WIDTH - OFFSET,CANVAS_HEIGHT - OFFSET);    //右边框
    }

    public void init(String productCode,String barCodeText,String workOrderNo,double reportLength){
        this.productCode = productCode;
        this.barCodeText = barCodeText;
        this.workOrderNo = workOrderNo;
        this.reportLength = reportLength;
    }

    public static PageFormat createPageFormat(){
        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.PORTRAIT);
        //    通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper p = new Paper();
        p.setSize(CANVAS_WIDTH,CANVAS_HEIGHT);//纸张大小
        p.setImageableArea(0,0, CANVAS_WIDTH,CANVAS_HEIGHT);//A4(595 X 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
        pf.setPaper(p);
        //    把 PageFormat 和 Printable 添加到书中，组成一个页面
        return pf;
    }
}
