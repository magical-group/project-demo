package UI;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import FuncClass.CCommondFunc;
import FuncClass.CDataMgr;
import HLDClient.CWebApiHandleBase;
import UI.CBaseEnum.KeyType;

public class frmDeliverLogin extends javax.swing.JPanel {

    boolean m_blNextClick = false;
    CustomControl.TextBoxInput m_Curtxt;
    String m_CurTabIndex;
    
    public frmDeliverLogin() {
        initComponents();
    }

    @Override
    public void paintComponent(Graphics g){    
        ImageIcon icon = new ImageIcon(getClass().getResource(CDataMgr.BackImg));
        g.drawImage(icon.getImage(), 0, 0, getSize().width, getSize().height, this);
    }
    
    void ClearData() {
        m_blNextClick = false;
        m_CurTabIndex = "1";
        m_Curtxt = txtDelieverPhone;
        txtDelieverPhone.Clear();
        txtDelieverPwd.Clear();
        lblTipMsg.setText("...");
        CBaseEnum.SetButtonStatus(btnExit, btnPreStep, 3);
    }
    
    void VoiceTip() {
        CCommondFunc.VoiceTip("请输入投递员登录信息-Please enter the sender login information");
    }
    
    void SetTextBox() {
        txtDelieverPhone.SetTextBox(1, 11);
        txtDelieverPwd.SetTextBox(2, 4);
        txtDelieverPhone.setFocusable(true);
    }

    public void TTkeyBoardInput(KeyType eKeyType, String strInput) {
        if (m_blNextClick) return ;
        
        FuncClass.CBaseTime.ReSetTime();// 重新计时
        
        if (eKeyType == CBaseEnum.KeyType.Key_TEXTBOX_FORCECHANGE || eKeyType == CBaseEnum.KeyType.Key_UP || eKeyType == CBaseEnum.KeyType.Key_DOWN) {
            AutoChangeForce(strInput);// 手动进行光标切换
        }
        else {
            // 按键信息
            switch (eKeyType) {
                case Key_BarCode:
                    break;
                case Key_NUMBER:
                    // 自动进行光标切换
                    if ("1".equals(m_CurTabIndex)) {
                        if (txtDelieverPhone.GetText().trim().length() == 11 && DelieverPhone_Validated()) {
                            m_CurTabIndex = "2";
                            m_Curtxt = txtDelieverPwd;// 验证通过自动跳转到下个文本框
                        }
                    }
                    m_Curtxt.InputText(strInput);
                    if ("2".equals(m_CurTabIndex)) {
                        if ((txtDelieverPhone.GetText().trim().length() == 11 && DelieverPhone_Validated()) 
                            && (txtDelieverPwd.GetText().trim().length() == 4 && DelieverPwd_Validated())
                            && DelieverPwd_Compare()) {
                            NextStep();// 验证通过自动调用下一步
                        }
                    }
                    break;
                case Key_SPACE:
                    m_Curtxt.InputText(strInput);
                    if ("2".equals(m_CurTabIndex) && "".equals(txtDelieverPwd.GetText())) {
                        AutoChangeForce("1");// 焦点自动切换回上一个文本框
                    }
                    break;
                case Key_ENTER:
                    if (DelieverPhone_Validated() && DelieverPwd_Validated() && DelieverPwd_Compare()) {
                        NextStep();// 验证通过自动调用下一步
                    }
                    break;
                case Key_ESC:
                    CDataMgr.MainHandle.OnEventShowForm(CBaseEnum.FormCase.Form_StandBy, CBaseEnum.RedirectType.Redirect_Null, null);
                    break;
            }
        }
    }
    
    void AutoChangeForce(String strInput) {
        m_CurTabIndex = strInput;
            
        switch (m_CurTabIndex) {
            case "1": m_Curtxt = txtDelieverPhone; break;
            case "2": m_Curtxt = txtDelieverPwd; break;
            case CBaseEnum.PIN_PRESSED_UP:
            case CBaseEnum.PIN_PRESSED_DOWN:
                if (m_Curtxt == txtDelieverPhone) {
                    m_CurTabIndex = "2"; m_Curtxt = txtDelieverPwd;
                }
                else {
                    m_CurTabIndex = "1"; m_Curtxt = txtDelieverPhone;
                }
                break;
        }
        
        m_Curtxt.SetCursor();
    }

    public void BeginForm(CBaseEnum.RedirectType eRedirectType, Object oParam) {
        ClearData();
        SetTextBox();
        VoiceTip();
        FuncClass.CBaseTime.StartTime(lblSeconds, 60);
        
        // 显示剩余格口
        int[] BoxCountItem = CCommondFunc.GetBoxRemain();
        int freeCount = BoxCountItem[0] + BoxCountItem[1] + BoxCountItem[2] + BoxCountItem[3] + BoxCountItem[4];
        lblUseBoxTotal.setText("共-totally:" + CCommondFunc.lpad(freeCount, 2) + "个可用");
        lblBigBox.setText("大箱子-big box:" + CCommondFunc.lpad(BoxCountItem[0], 2) + "个");
        lblNormalBox.setText("中箱子-middle box:" + CCommondFunc.lpad(BoxCountItem[1], 2) + "个");
        lblSmallBox.setText("小箱子-small box:" + CCommondFunc.lpad(BoxCountItem[2], 2) + "个");
        lblSBigBox.setText("超大箱子-super big box:" + CCommondFunc.lpad(BoxCountItem[3], 2) + "个");
        lblSSmallBox.setText("超小箱子-super small box:" + CCommondFunc.lpad(BoxCountItem[4], 2) + "个");
        
        if (CDataMgr.IsSoftFistRun) {
            CDataMgr.IsSoftFistRun = false;
            // 数据统计
            onSJTJ(freeCount);
        }
    }

    void onSJTJ(int freeCount) {
        int qb = 0, ky_fwq = 0, ky_zw = 0, sy = 0, dds = 0, kxyw = 0, gz = 0, yc = 0;
        
        qb = CCommondFunc.FilterBoxCount(null);// 全部
        dds = CCommondFunc.GetOrderCount_Use();// 订单数
        sy = CCommondFunc.FilterBoxCount("and fi_BoxStatus=" + + CBaseEnum.BoxStatus.Box_Busy.ordinal());// 使用 2
        ky_zw = CCommondFunc.FilterBoxCount("and fi_BoxStatus=" + CBaseEnum.BoxStatus.Box_Ideal.ordinal());// 空闲 1
        gz  = CCommondFunc.FilterBoxCount("and fi_BoxStatus=" + CBaseEnum.BoxStatus.Box_Fault.ordinal());// 故障 3
        kxyw = CCommondFunc.FilterBoxCount("and fi_BoxStatus=" + CBaseEnum.BoxStatus.Box_Ideal.ordinal() + " and fi_Infrared=" + CBaseEnum.Infrared.NoThing.ordinal());// 空闲有物
        
        ky_fwq = freeCount;// 可用
        yc = qb - ky_fwq - dds;// 异常
        
        float gzl = 0; 
        try { gzl = (yc * 100) / qb; } catch (Exception ex){}
        CSystemDAO.getInstance().AddWebLog(CBaseEnum.SystemLog_Normal, "数据统计,故障率-Data statistics, failure rate:"  + String.valueOf(gzl) + "%" +
                                                                            " 总格口-Total lattices:" + String.valueOf(qb) + 
                                                                            " 异常格口-Abnormal lattices-:" + String.valueOf(yc) +  
                                                                            " 可用格口(服务器)-Available Lattice Port (Server):" + String.valueOf(ky_fwq) +  
                                                                            " 在柜订单:-Counter order" +  String.valueOf(dds) +
                                                                            " 使用格口:-use lattices" +  String.valueOf(sy) +
                                                                            " 空闲格口:-free lattices" + String.valueOf(ky_zw) + 
                                                                            " 故障格口:-fault lattices" + String.valueOf(gz) +
                                                                            " 空闲有物:-Idle things" + String.valueOf(kxyw));
        
    }
    
    public void PacketInput_KDYRZ(String err) {
        if (!"".equals(err)) {
            m_blNextClick = false;
            lblTipMsg.setText("认证失败-Authentication failed:" + err);// 错误输出
            CBaseEnum.SetButtonStatus(btnExit, btnPreStep, 3);
        }
    }
    
    boolean DelieverPhone_Validated() {
        return CCommondFunc.Phone_Validated(txtDelieverPhone.GetText(), lblTipMsg, "手机号-phone number");
    }
    
    boolean DelieverPwd_Validated() {
        String pwd = txtDelieverPwd.GetText();
        if ("".equals(pwd)) {
            lblTipMsg.setText("4位校验码不能为空，请重新输入！-4-bit check code can not be empty, please re-enter!");
            return false;
        }
        else {
            if (4 != pwd.length()) {
                lblTipMsg.setText("4位校验码格式不正确，请重新输入！4-bit check code format is incorrect, please re-enter!");
                return false;
            }
        }
        
        lblTipMsg.setText("...");    
        return true;
    }
    
    boolean DelieverPwd_Compare() {
        if (!txtDelieverPwd.GetText().equals(CCommondFunc.GetCheckPhone(txtDelieverPhone.GetText()))) {
            lblTipMsg.setText("4位校验码校验失败，请重新输入！-4-bit check code failed, please re-enter!");
            return false;
        }
        
        lblTipMsg.setText("..."); 
        return true;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExit = new javax.swing.JButton();
        btnPreStep = new javax.swing.JButton();
        lblTimeOut1 = new javax.swing.JLabel();
        lblSeconds = new javax.swing.JLabel();
        lblTimeOut2 = new javax.swing.JLabel();
        lblDelieverPhone = new javax.swing.JLabel();
        lblDelieverPwd = new javax.swing.JLabel();
        numberKeyPad1 = new CustomControl.NumberKeyPad();
        lblTitle = new javax.swing.JLabel();
        txtDelieverPhone = new CustomControl.TextBoxInput();
        txtDelieverPwd = new CustomControl.TextBoxInput();
        pnlTipMsg = new javax.swing.JPanel();
        lblTipMsg = new javax.swing.JLabel();
        pnlBoxCount = new javax.swing.JPanel();
        lblUseBoxTotal = new javax.swing.JLabel();
        lblSBigBox = new javax.swing.JLabel();
        lblSSmallBox = new javax.swing.JLabel();
        lblBigBox = new javax.swing.JLabel();
        lblNormalBox = new javax.swing.JLabel();
        lblSmallBox = new javax.swing.JLabel();

        setBackground(new java.awt.Color(6, 57, 104));

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/btnExit.png"))); // NOI18N
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnPreStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/btnBack.png"))); // NOI18N
        btnPreStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreStepActionPerformed(evt);
            }
        });

        lblTimeOut1.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblTimeOut1.setForeground(new java.awt.Color(255, 255, 255));
        lblTimeOut1.setText("执行操作界面还剩");

        lblSeconds.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblSeconds.setForeground(new java.awt.Color(255, 0, 0));
        lblSeconds.setText("60");

        lblTimeOut2.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblTimeOut2.setForeground(new java.awt.Color(255, 255, 255));
        lblTimeOut2.setText("秒，即将退出操作返回首页");

        lblDelieverPhone.setFont(new java.awt.Font("微软雅黑", 0, 24)); // NOI18N
        lblDelieverPhone.setForeground(new java.awt.Color(255, 255, 255));
        lblDelieverPhone.setText("手机号:");

        lblDelieverPwd.setFont(new java.awt.Font("微软雅黑", 0, 24)); // NOI18N
        lblDelieverPwd.setForeground(new java.awt.Color(255, 255, 255));
        lblDelieverPwd.setText("4位校验码:");

        lblTitle.setFont(new java.awt.Font("微软雅黑", 0, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("投递员登陆");

        pnlTipMsg.setPreferredSize(new java.awt.Dimension(1024, 39));

        lblTipMsg.setFont(new java.awt.Font("微软雅黑", 0, 24)); // NOI18N
        lblTipMsg.setForeground(new java.awt.Color(255, 0, 0));
        lblTipMsg.setText("...");

        javax.swing.GroupLayout pnlTipMsgLayout = new javax.swing.GroupLayout(pnlTipMsg);
        pnlTipMsg.setLayout(pnlTipMsgLayout);
        pnlTipMsgLayout.setHorizontalGroup(
            pnlTipMsgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTipMsgLayout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addComponent(lblTipMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(188, 188, 188))
        );
        pnlTipMsgLayout.setVerticalGroup(
            pnlTipMsgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTipMsgLayout.createSequentialGroup()
                .addComponent(lblTipMsg)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        lblTipMsg.getAccessibleContext().setAccessibleName("");

        pnlBoxCount.setBackground(new java.awt.Color(15, 68, 120));

        lblUseBoxTotal.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblUseBoxTotal.setForeground(java.awt.Color.red);
        lblUseBoxTotal.setText("共:00个可用");

        lblSBigBox.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblSBigBox.setForeground(new java.awt.Color(255, 255, 255));
        lblSBigBox.setText("超大箱子:00个");

        lblSSmallBox.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblSSmallBox.setForeground(new java.awt.Color(255, 255, 255));
        lblSSmallBox.setText("超小箱子:00个");

        lblBigBox.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblBigBox.setForeground(new java.awt.Color(255, 255, 255));
        lblBigBox.setText("大箱子:00个");

        lblNormalBox.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblNormalBox.setForeground(new java.awt.Color(255, 255, 255));
        lblNormalBox.setText("中箱子:00个");

        lblSmallBox.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblSmallBox.setForeground(new java.awt.Color(255, 255, 255));
        lblSmallBox.setText("小箱子:00个");

        javax.swing.GroupLayout pnlBoxCountLayout = new javax.swing.GroupLayout(pnlBoxCount);
        pnlBoxCount.setLayout(pnlBoxCountLayout);
        pnlBoxCountLayout.setHorizontalGroup(
            pnlBoxCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBoxCountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBoxCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSBigBox)
                    .addComponent(lblSSmallBox)
                    .addComponent(lblUseBoxTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlBoxCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBoxCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblNormalBox)
                        .addComponent(lblSmallBox))
                    .addComponent(lblBigBox, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnlBoxCountLayout.setVerticalGroup(
            pnlBoxCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBoxCountLayout.createSequentialGroup()
                .addGroup(pnlBoxCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSBigBox)
                    .addComponent(lblBigBox, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBoxCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNormalBox)
                    .addComponent(lblUseBoxTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBoxCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSmallBox)
                    .addComponent(lblSSmallBox)))
        );

        lblUseBoxTotal.getAccessibleContext().setAccessibleName("");
        lblSBigBox.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTipMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitle)
                .addGap(407, 407, 407))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTimeOut1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSeconds, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTimeOut2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPreStep, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDelieverPwd)
                            .addComponent(lblDelieverPhone))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlBoxCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDelieverPwd, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDelieverPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numberKeyPad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(lblTitle)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDelieverPhone)
                        .addGap(11, 11, 11))
                    .addComponent(txtDelieverPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDelieverPwd)
                        .addGap(9, 9, 9))
                    .addComponent(txtDelieverPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberKeyPad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(pnlBoxCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTipMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPreStep, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTimeOut1)
                            .addComponent(lblSeconds)
                            .addComponent(lblTimeOut2))
                        .addGap(12, 12, 12)))
                .addGap(15, 15, 15))
        );

        lblSeconds.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        TTkeyBoardInput(KeyType.Key_ESC, "");
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnPreStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreStepActionPerformed
        CDataMgr.IsTDY = true;
        CDataMgr.MainHandle.OnEventShowForm(CBaseEnum.FormCase.Form_Agree, CBaseEnum.RedirectType.Redirect_Next, null);
    }//GEN-LAST:event_btnPreStepActionPerformed
    
    void NextStep() {
        m_blNextClick = true;
        lblTipMsg.setText("认证中,若出现网络故障,请稍后重新登录...-In authentication, if there is a network failure, please login again later.");
        CBaseEnum.SetButtonStatus(btnExit, btnPreStep, 0);
        CDataMgr.TDYPhone = txtDelieverPhone.GetText();
        CDataMgr.TDYPwd = txtDelieverPwd.GetText();
        CDataMgr.LocalPwdType = 6;
        CWebApiHandleBase.Process6001(CBaseEnum.Auth.Step1);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnPreStep;
    private javax.swing.JLabel lblBigBox;
    private javax.swing.JLabel lblDelieverPhone;
    private javax.swing.JLabel lblDelieverPwd;
    private javax.swing.JLabel lblNormalBox;
    private javax.swing.JLabel lblSBigBox;
    private javax.swing.JLabel lblSSmallBox;
    private javax.swing.JLabel lblSeconds;
    private javax.swing.JLabel lblSmallBox;
    private javax.swing.JLabel lblTimeOut1;
    private javax.swing.JLabel lblTimeOut2;
    private javax.swing.JLabel lblTipMsg;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUseBoxTotal;
    private CustomControl.NumberKeyPad numberKeyPad1;
    private javax.swing.JPanel pnlBoxCount;
    private javax.swing.JPanel pnlTipMsg;
    private CustomControl.TextBoxInput txtDelieverPhone;
    private CustomControl.TextBoxInput txtDelieverPwd;
    // End of variables declaration//GEN-END:variables
}
