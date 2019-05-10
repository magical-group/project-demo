package UI;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import FuncClass.CCommondFunc;
import FuncClass.CDataMgr;
import static UI.CBaseEnum.KeyType.Key_BarCode;
import txt.CTxtHelp;

public class frmDeliverTackStep1 extends javax.swing.JPanel {
    
    boolean m_blNextClick;
    
    public frmDeliverTackStep1() {
        initComponents();
    }
    
    @Override
    public void paintComponent(Graphics g){
        ImageIcon icon = new ImageIcon(getClass().getResource(CDataMgr.BackImg));
        g.drawImage(icon.getImage(), 0, 0, getSize().width, getSize().height, this);
    }

    void VoiceTip() {
        CCommondFunc.VoiceTip("请扫描或输入包裹条码进行取件-Please scan or enter the package bar code to fetch the package.");
    }
    
    void SetTextBox() {
        txtOrderID.SetTextBox(1, 20);
        txtOrderID.setFocusable(true);
    }
    
    public void TTkeyBoardInput(CBaseEnum.KeyType eKeyType, String strInput) {
        if (m_blNextClick) return;
        
        FuncClass.CBaseTime.ReSetTime();// 重新计时
        
        // 按键信息
        switch (eKeyType) {
            case Key_BarCode:
                txtOrderID.SetDefaultValue(strInput);
                if (txtOrderID_Validated()) {
                    NextStep();// 验证通过自动调用下一步
                }
                break;
            case Key_NUMBER:
                if (!"".equals(strInput)) {
                    txtOrderID.InputText(strInput);
                    if (txtOrderID.GetText().trim().length() == 20 && txtOrderID_Validated()) {
                        NextStep();// 验证通过自动调用下一步
                    } 
                }
                break;
            case Key_SPACE:
                txtOrderID.InputText(strInput);
                break;
            case Key_ENTER:
                if (txtOrderID.GetText().length() > 1) {
                    if (txtOrderID_Validated()) {
                        NextStep();// 验证通过自动调用下一步
                    }
                }
                else {
                    lblTipMsg.setText("包裹号不能为空!-The package number should not be empty!");
                }
                break;
            case Key_ESC:
                CDataMgr.MainHandle.OnEventShowForm(CBaseEnum.FormCase.Form_StandBy, CBaseEnum.RedirectType.Redirect_Null, null);
                break;
        }
    }
    
    public void BeginForm(CBaseEnum.RedirectType eRedirectType, Object oParam) {
        m_blNextClick = false;
        
        txtOrderID.Clear();
        
        lblTipMsg.setText("...");
        SetTextBox();
        FuncClass.CBaseTime.StartTime(lblSeconds, 60);
        // 加载全键盘
        CDataMgr.AllKeyPadHandle = zTKeyPad1;
        VoiceTip();
        CDataMgr.BatchTake = false;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        lblTimeOut1 = new javax.swing.JLabel();
        lblSeconds = new javax.swing.JLabel();
        lblTimeOut2 = new javax.swing.JLabel();
        btnPreStep = new javax.swing.JButton();
        txtOrderID = new CustomControl.TextBoxInput();
        lblTitle1 = new javax.swing.JLabel();
        zTKeyPad1 = new CustomControl.ZTKeyPad();
        btnBatchTack = new javax.swing.JButton();
        pnlTipMsg = new javax.swing.JPanel();
        lblTipMsg = new javax.swing.JLabel();

        setBackground(new java.awt.Color(6, 57, 104));

        lblTitle.setBackground(new java.awt.Color(6, 57, 104));
        lblTitle.setFont(new java.awt.Font("微软雅黑", 0, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("取回包裹");

        btnExit.setBackground(new java.awt.Color(6, 57, 104));
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/btnExit.png"))); // NOI18N
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        lblTimeOut1.setBackground(new java.awt.Color(6, 57, 104));
        lblTimeOut1.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblTimeOut1.setForeground(new java.awt.Color(255, 255, 255));
        lblTimeOut1.setText("执行操作界面还剩");

        lblSeconds.setBackground(new java.awt.Color(6, 57, 104));
        lblSeconds.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblSeconds.setForeground(new java.awt.Color(255, 0, 0));
        lblSeconds.setText("60");

        lblTimeOut2.setBackground(new java.awt.Color(6, 57, 104));
        lblTimeOut2.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lblTimeOut2.setForeground(new java.awt.Color(255, 255, 255));
        lblTimeOut2.setText("秒，即将退出操作返回首页");

        btnPreStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/btnBack.png"))); // NOI18N
        btnPreStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreStepActionPerformed(evt);
            }
        });

        lblTitle1.setBackground(new java.awt.Color(6, 57, 104));
        lblTitle1.setFont(new java.awt.Font("微软雅黑", 0, 36)); // NOI18N
        lblTitle1.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle1.setText("包裹条码:");

        btnBatchTack.setFont(new java.awt.Font("微软雅黑", 1, 18)); // NOI18N
        btnBatchTack.setText("批量取件");
        btnBatchTack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatchTackActionPerformed(evt);
            }
        });

        lblTipMsg.setFont(new java.awt.Font("黑体", 0, 24)); // NOI18N
        lblTipMsg.setForeground(new java.awt.Color(255, 0, 0));
        lblTipMsg.setText("...");

        javax.swing.GroupLayout pnlTipMsgLayout = new javax.swing.GroupLayout(pnlTipMsg);
        pnlTipMsg.setLayout(pnlTipMsgLayout);
        pnlTipMsgLayout.setHorizontalGroup(
            pnlTipMsgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTipMsgLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(lblTipMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTipMsgLayout.setVerticalGroup(
            pnlTipMsgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTipMsgLayout.createSequentialGroup()
                .addComponent(lblTipMsg)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTipMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(lblTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(zTKeyPad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTimeOut1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSeconds)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTimeOut2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPreStep, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(lblTitle1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBatchTack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtOrderID, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitle1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBatchTack, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(zTKeyPad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlTipMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPreStep, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTimeOut1)
                            .addComponent(lblSeconds)
                            .addComponent(lblTimeOut2))))
                .addGap(15, 15, 15))
        );

        lblTitle.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        TTkeyBoardInput(CBaseEnum.KeyType.Key_ESC, "");
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnPreStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreStepActionPerformed
        CDataMgr.MainHandle.OnEventShowForm(CBaseEnum.FormCase.Form_DeliverSelect, CBaseEnum.RedirectType.Redirect_Next, null);
    }//GEN-LAST:event_btnPreStepActionPerformed

    private void btnBatchTackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatchTackActionPerformed
        CDataMgr.MainHandle.OnEventShowForm(CBaseEnum.FormCase.Form_DeliverTackStep11, CBaseEnum.RedirectType.Redirect_Next, null);
    }//GEN-LAST:event_btnBatchTackActionPerformed
    
    boolean txtOrderID_Validated() {
        String strWhere = " and fi_DeviceID=" + CDataMgr.DeviceID + " and fi_Status=" + CBaseEnum.Package_DeliverComplete;
        CDataMgr.TDYOrderID = CCommondFunc.OrderID_Deal(txtOrderID.GetText());
        boolean ret =  CCommondFunc.txtOrderID_Validated(CDataMgr.TDYOrderID, strWhere, lblTipMsg, 1);
        if (!ret) {
            lblTipMsg.setText("不存在该包裹号信息，请重新输入！-The package number information does not exist, please re-enter!");
            return false;
        }
        
        // 更新表tb_Order，逾期取件模式fi_PickUpFlag=2;BGetA
        String strSql = "update tb_Order set fs_PickUpUid='" +  CDataMgr.TDYPhone + "',fs_PickUpPwd='" + CDataMgr.TDYPwd + "'" +
                        " where fs_OrderID='" + CDataMgr.TDYOrderID + "' and fi_Status=" + CBaseEnum.Package_DeliverComplete + " and fi_DeviceID=" + CDataMgr.DeviceID;
        func.CCommondFunc.SyncExecuteSql(strSql);
   
        return true;
    }
    
    void NextStep() {
        m_blNextClick = true;
        
        lblTipMsg.setText("更新中...");
        CTxtHelp.AppendLog("[Info] ===============================================================");
        CTxtHelp.AppendLog("[UI] DeliverTackStep1,OrderID=" + CDataMgr.TDYOrderID);
        //
        CBoxProperty property = CCommondFunc.GetOrderBox(CDataMgr.TDYOrderID, "");
        if (null != property) {
            CLogicHandle.OpenBox(property, CBaseEnum.Lock_DispRevokeOpen, CDataMgr.TDYPhone, CBaseEnum.Action_TDYQJ, "投递员单个取件开箱门-Deliverer's single pick-up door");
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatchTack;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnPreStep;
    private javax.swing.JLabel lblSeconds;
    private javax.swing.JLabel lblTimeOut1;
    private javax.swing.JLabel lblTimeOut2;
    private javax.swing.JLabel lblTipMsg;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JPanel pnlTipMsg;
    private CustomControl.TextBoxInput txtOrderID;
    private CustomControl.ZTKeyPad zTKeyPad1;
    // End of variables declaration//GEN-END:variables
}
