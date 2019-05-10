package UI;

import CustomControl.JTableData;
import DB.CDBHelper;
import DB.QueryEntity;
import FuncClass.CCommondFunc;
import FuncClass.CDataMgr;
import UI.CBaseEnum.FormCase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import mydate.CDateHelper;
import txt.CTxtHelp;

public class frmOrderFiler extends javax.swing.JPanel {

    CFormPassParam param;
    boolean m_bkeyclick;
    FormCase m_formCase;
    JTable tableData;
    String[] columnNames = { "运单号-Tracking Number", "格口号-Lattice Number", "派件时间-delivery time", "投递员-存-deliver store", "投递员-取-deliver take", "取件时间-take time", "用户-user" };
    final String m_strFileds = "fs_OrderID,fi_BoxID,substr(fs_DeliverTime,6,11) as fs_DeliverTime,fs_DeliverUid,fs_PickUpUid,substr(fs_PickUpTime,6,11) as fs_PickUpTime,fs_Phone";
    final int PageSize = 7;  //分页行数
    int CurrentPageIndex = 1;   //现在选取的分页编号
    int TotalPage = 0;  //总共多少分页fi_ID
    String m_strWhere = "";
    String m_strWhereTemp = "";
    String m_strOrderID = "";
    
    public frmOrderFiler() {
        initComponents();
    }
    
    // 设置是否显示日期过滤条件
    void SetPnlTime(boolean flg) {
        lblTime.setVisible(flg); dateChooser1.setVisible(flg); lblTo.setVisible(flg); dateChooser2.setVisible(flg);
    }
    
    void ClearData() {
        txtOrderID.Clear();
        lblTipMsg.setText("...");
    }
    
    void VoiceTip() {
        FuncClass.CCommondFunc.VoiceTip("请扫描或输入包裹条码进行包裹查询-Please scan or enter the package bar code for package inquiry");
    }
    
    void SetTextBox() {
        txtOrderID.SetTextBox(1, 20);
        txtOrderID.setFocusable(true);
    }
    
    public void TTkeyBoardInput(CBaseEnum.KeyType eKeyType, String strInput) {
        FuncClass.CBaseTime.ReSetTime();// 重新计时
        
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
                if (txtOrderID_Validated()) {
                    NextStep();// 验证通过自动调用下一步
                }
                break;
            case Key_ESC:
                CDataMgr.MainHandle.OnEventShowForm(CBaseEnum.FormCase.Form_StandBy, CBaseEnum.RedirectType.Redirect_Null, null);
                break;
        }
    }
    
    boolean txtOrderID_Validated() {
        m_strOrderID = CCommondFunc.OrderID_Deal(txtOrderID.GetText());
        txtOrderID.SetDefaultValue(m_strOrderID);
        
        return true;
    }
    
    public void BeginForm(CBaseEnum.RedirectType eRedirectType, Object oParam) {
        if (null == oParam) return;
        
        try {
            CSystemDAO.getInstance().OpenBarCode();
            ClearData();
            SetTextBox();
            FuncClass.CBaseTime.StartTime(lblSeconds, 60);
            // 加载全键盘
            CDataMgr.AllKeyPadHandle = zTKeyPad1;

            m_formCase = (FormCase)oParam;

            m_strWhereTemp = "fi_DeviceID=" + CDataMgr.DeviceID;
            if (m_formCase== FormCase.Form_DeliverSelect) {
                lblTitle.setText("[快递公司-company:" + CDataMgr.KDGS + "] 运单号查询-check the tracking number");
                m_strWhereTemp = m_strWhereTemp + " and fi_UnitID ='" + CDataMgr.KDGS + "'";
                dateChooser1.SetDate(CDateHelper.getInstance().DateCalculate(-7, null));
                SetPnlTime(true);
                CDataMgr.AllKeyPadHandle.OnEventShowForm(CBaseEnum.FormCase.Form_ListViewPad);// 显示数据
                btnOKActionPerformed(null);// 调用查询功能
            }
            else {
                lblTitle.setText("[用户] 运单号查询-user tracking number");
                SetPnlTime(false);
                CDataMgr.AllKeyPadHandle.OnEventShowForm(CBaseEnum.FormCase.Form_ZMKeyPad);// 显示字符
            }
            
            VoiceTip();
        }
        catch (Exception e)  {
            String err = "页面跳转异常,OrderFiler:" + e.getMessage();
            txt.CTxtHelp.AppendLog("[Error]" + err);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblOrderID = new javax.swing.JLabel();
        txtOrderID = new CustomControl.TextBoxInput();
        lblTitle = new javax.swing.JLabel();
        dateChooser1 = new CustomControl.DateChooser();
        lblTime = new javax.swing.JLabel();
        lblTo = new javax.swing.JLabel();
        dateChooser2 = new CustomControl.DateChooser();
        btnFirstPage = new javax.swing.JButton();
        btnPrevPage = new javax.swing.JButton();
        btnNextPage = new javax.swing.JButton();
        btnLastPage = new javax.swing.JButton();
        lbCurrentPage = new javax.swing.JLabel();
        lblSpace = new javax.swing.JLabel();
        lbTotalPage = new javax.swing.JLabel();
        btnOK = new javax.swing.JButton();
        btnKeyBox = new javax.swing.JButton();
        zTKeyPad1 = new CustomControl.ZTKeyPad();
        lblTimeOut1 = new javax.swing.JLabel();
        lblSeconds = new javax.swing.JLabel();
        lblTimeOut2 = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        btnPreStep = new javax.swing.JButton();
        pnlTipMsg = new javax.swing.JPanel();
        lblTipMsg = new javax.swing.JLabel();

        setBackground(new java.awt.Color(6, 57, 104));

        lblOrderID.setBackground(new java.awt.Color(6, 57, 104));
        lblOrderID.setFont(new java.awt.Font("微软雅黑", 0, 30)); // NOI18N
        lblOrderID.setForeground(new java.awt.Color(255, 255, 255));
        lblOrderID.setText("包裹条码:");

        lblTitle.setBackground(new java.awt.Color(6, 57, 104));
        lblTitle.setFont(new java.awt.Font("微软雅黑", 0, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("[快递公司:0001] 运单号查询");

        lblTime.setBackground(new java.awt.Color(6, 57, 104));
        lblTime.setFont(new java.awt.Font("微软雅黑", 0, 30)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setText("投递时间:");

        lblTo.setBackground(new java.awt.Color(6, 57, 104));
        lblTo.setFont(new java.awt.Font("微软雅黑", 0, 30)); // NOI18N
        lblTo.setForeground(new java.awt.Color(255, 255, 255));
        lblTo.setText("到");

        btnFirstPage.setFont(new java.awt.Font("微软雅黑", 1, 18)); // NOI18N
        btnFirstPage.setText("首页");
        btnFirstPage.setMaximumSize(new java.awt.Dimension(87, 33));
        btnFirstPage.setMinimumSize(new java.awt.Dimension(87, 33));
        btnFirstPage.setPreferredSize(new java.awt.Dimension(87, 33));
        btnFirstPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstPageActionPerformed(evt);
            }
        });

        btnPrevPage.setFont(new java.awt.Font("微软雅黑", 1, 18)); // NOI18N
        btnPrevPage.setText("上一页");
        btnPrevPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevPageActionPerformed(evt);
            }
        });

        btnNextPage.setFont(new java.awt.Font("微软雅黑", 1, 18)); // NOI18N
        btnNextPage.setText("下一页");
        btnNextPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextPageActionPerformed(evt);
            }
        });

        btnLastPage.setFont(new java.awt.Font("微软雅黑", 1, 18)); // NOI18N
        btnLastPage.setLabel("末页");
        btnLastPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastPageActionPerformed(evt);
            }
        });

        lbCurrentPage.setFont(new java.awt.Font("微软雅黑", 0, 24)); // NOI18N
        lbCurrentPage.setForeground(new java.awt.Color(255, 255, 255));
        lbCurrentPage.setText("0");

        lblSpace.setFont(new java.awt.Font("微软雅黑", 0, 24)); // NOI18N
        lblSpace.setForeground(new java.awt.Color(255, 255, 255));
        lblSpace.setText("/");

        lbTotalPage.setFont(new java.awt.Font("微软雅黑", 0, 24)); // NOI18N
        lbTotalPage.setForeground(new java.awt.Color(255, 255, 255));
        lbTotalPage.setText("0");

        btnOK.setFont(new java.awt.Font("微软雅黑", 1, 12)); // NOI18N
        btnOK.setText("确定");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnKeyBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/Keyboard.png"))); // NOI18N
        btnKeyBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeyBoxActionPerformed(evt);
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

        btnExit.setBackground(new java.awt.Color(6, 57, 104));
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

        pnlTipMsg.setBackground(new java.awt.Color(6, 57, 104));
        pnlTipMsg.setPreferredSize(new java.awt.Dimension(1024, 39));

        lblTipMsg.setBackground(new java.awt.Color(6, 57, 104));
        lblTipMsg.setFont(new java.awt.Font("微软雅黑", 0, 24)); // NOI18N
        lblTipMsg.setForeground(new java.awt.Color(255, 0, 0));
        lblTipMsg.setText("...");

        javax.swing.GroupLayout pnlTipMsgLayout = new javax.swing.GroupLayout(pnlTipMsg);
        pnlTipMsg.setLayout(pnlTipMsgLayout);
        pnlTipMsgLayout.setHorizontalGroup(
            pnlTipMsgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTipMsgLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblTipMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
        );
        pnlTipMsgLayout.setVerticalGroup(
            pnlTipMsgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTipMsgLayout.createSequentialGroup()
                .addComponent(lblTipMsg)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(zTKeyPad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addComponent(btnFirstPage, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrevPage, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(lbCurrentPage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSpace)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbTotalPage)
                        .addGap(29, 29, 29)
                        .addComponent(btnNextPage, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLastPage, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(btnPreStep, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTime)
                    .addComponent(lblOrderID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKeyBox, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlTipMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(146, 146, 146))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(lblTitle)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblOrderID)
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTime)
                            .addComponent(lblTo, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtOrderID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnKeyBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(dateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlTipMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(zTKeyPad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFirstPage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrevPage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNextPage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLastPage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbCurrentPage)
                            .addComponent(lblSpace)
                            .addComponent(lbTotalPage))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPreStep, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTimeOut1)
                                    .addComponent(lblSeconds)
                                    .addComponent(lblTimeOut2))
                                .addGap(14, 14, 14))))
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        lblTime.getAccessibleContext().setAccessibleName("");
        btnFirstPage.getAccessibleContext().setAccessibleName("");
        btnPrevPage.getAccessibleContext().setAccessibleName("");
        btnNextPage.getAccessibleContext().setAccessibleName("");
        btnLastPage.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void btnFirstPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstPageActionPerformed
        // 首页
        CurrentPageIndex = 1;
        GetCurrentRecords(CurrentPageIndex);
    }//GEN-LAST:event_btnFirstPageActionPerformed

    private void btnPrevPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevPageActionPerformed
        // 上一页
        if (this.CurrentPageIndex > 1) {
            CurrentPageIndex--;
            GetCurrentRecords(CurrentPageIndex);
        }
    }//GEN-LAST:event_btnPrevPageActionPerformed

    private void btnNextPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextPageActionPerformed
        // 下一页
        if (CurrentPageIndex < TotalPage) {
            CurrentPageIndex++;
            GetCurrentRecords(CurrentPageIndex);
        }
    }//GEN-LAST:event_btnNextPageActionPerformed

    private void btnLastPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastPageActionPerformed
        // 末页
        CurrentPageIndex = TotalPage;
        GetCurrentRecords(CurrentPageIndex);
    }//GEN-LAST:event_btnLastPageActionPerformed

    private void btnKeyBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeyBoxActionPerformed
        FuncClass.CBaseTime.ReSetTime();// 重新计时
        
        if (!m_bkeyclick) {
            CDataMgr.AllKeyPadHandle.OnEventShowForm(CBaseEnum.FormCase.Form_ZMKeyPad);// 显示字符
        }
        else {
            CDataMgr.AllKeyPadHandle.OnEventShowForm(CBaseEnum.FormCase.Form_ListViewPad);// 显示数据
        }
        
        m_bkeyclick = !m_bkeyclick;
    }//GEN-LAST:event_btnKeyBoxActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        TTkeyBoardInput(CBaseEnum.KeyType.Key_ESC, "");
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        TTkeyBoardInput(CBaseEnum.KeyType.Key_ENTER, "");
    }//GEN-LAST:event_btnOKActionPerformed

    private void btnPreStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreStepActionPerformed
        CDataMgr.MainHandle.OnEventShowForm(m_formCase, CBaseEnum.RedirectType.Redirect_Pre, null);
    }//GEN-LAST:event_btnPreStepActionPerformed

    void NextStep() {
        String strWhere = "";
        CurrentPageIndex = 1;
        
        switch (m_formCase)
        {
            case Form_DeliverSelect:
                // 快递员查询需登录
                if (!"".equals(m_strOrderID))  strWhere = " and fs_OrderID='" + m_strOrderID + "'";
                strWhere = strWhere + " and fs_DeliverTime>='" + (new SimpleDateFormat("yyyy-MM-dd 00:00:00.000").format(dateChooser1.getDate())) + "'";
                strWhere = strWhere + " and fs_DeliverTime<='" + (new SimpleDateFormat("yyyy-MM-dd 23:59:59.000").format(dateChooser2.getDate())) + "'";
                break;
            case Form_UserPackageStep1: 
                // 用户查询需要输入运单号
                if (!"".equals(m_strOrderID)) {
                    strWhere = " and fs_OrderID='" + m_strOrderID + "'";
                }
                else {
                    strWhere = "and 1<>1";
                }
                break;
        }
        
        m_strWhere = m_strWhereTemp + strWhere;
        GetTotalPages();
        btnFirstPageActionPerformed(null);
    }
    
    // 计算页数
    void GetTotalPages() {
        String strSql = "select count(fi_ID) as nCount from tb_Order where " + m_strWhere + " order by fi_ID desc";
        QueryEntity result = CDBHelper.getInstance().Query(strSql); // 查询数据
        if (!result.hasData) { CTxtHelp.AppendLog("[Error] <GetTotalPages_OrderFiler> sql=" + strSql); return ; }
        
        int rowCount = 0;
        ResultSet rs = result.dataRs;;
        try { if (rs.next()) { rowCount = CCommondFunc.GetIntDB(rs.getString("nCount")); } } 
        catch (SQLException e) { CTxtHelp.AppendLog("[Error]SQLException,errmsg:" + e.getMessage()); }
        finally { CDBHelper.getInstance().closeQuery(result); }
        
        TotalPage = rowCount / PageSize;
        if (rowCount % PageSize > 0) TotalPage += 1;//不足一個分頁行數的還是算一頁
        lbTotalPage.setText(String.valueOf(TotalPage));
        
        if (0 == rowCount && null != txtOrderID.GetText() && !"".equals(txtOrderID.GetText())) 
        {
            lblTipMsg.setText("查无该包裹信息!");
            CDataMgr.AllKeyPadHandle.OnEventShowForm(CBaseEnum.FormCase.Form_ZMKeyPad);// 显示字符
        }
        else {
            CDataMgr.AllKeyPadHandle.OnEventShowForm(CBaseEnum.FormCase.Form_ListViewPad);// 数据显示
        }
    }
    
    //取得該分頁資料
    private void GetCurrentRecords(int page) {
        FuncClass.CBaseTime.ReSetTime();// 重新计时
        
        String strSql = "";
        if (page == 1)
           strSql = "select " + m_strFileds + " from tb_Order where " + m_strWhere + " order by fi_ID desc limit 0, " + PageSize;
        else {
            int PreviousPageOffSet = (page - 1) * PageSize;
            strSql = "select " + m_strFileds + " from tb_Order where " + m_strWhere +
                    " and fi_ID not in (select fi_ID from tb_Order where " + m_strWhere + " order by fi_ID desc limit 0, " + PreviousPageOffSet + ")" +
                    " order by fi_ID desc limit 0, " + PageSize;
        }

        QueryEntity result = CDBHelper.getInstance().QueryWithCount(strSql);
        if (!result.hasData) { CTxtHelp.AppendLog("[Error] <GetCurrentRecords_OrderFiler> sql=" + strSql); lbCurrentPage.setText("0"); return ; }
        
        lbCurrentPage.setText(String.valueOf(CurrentPageIndex));
        Object[][] rowData = JTableData.resultSetToObjectArray(result, ""); 
        CDataMgr.AllKeyPadHandle.GetTable().setModel(new DefaultTableModel(rowData, columnNames){
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnFirstPage;
    private javax.swing.JButton btnKeyBox;
    private javax.swing.JButton btnLastPage;
    private javax.swing.JButton btnNextPage;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnPreStep;
    private javax.swing.JButton btnPrevPage;
    private CustomControl.DateChooser dateChooser1;
    private CustomControl.DateChooser dateChooser2;
    private javax.swing.JLabel lbCurrentPage;
    private javax.swing.JLabel lbTotalPage;
    private javax.swing.JLabel lblOrderID;
    private javax.swing.JLabel lblSeconds;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTimeOut1;
    private javax.swing.JLabel lblTimeOut2;
    private javax.swing.JLabel lblTipMsg;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTo;
    private javax.swing.JPanel pnlTipMsg;
    private CustomControl.TextBoxInput txtOrderID;
    private CustomControl.ZTKeyPad zTKeyPad1;
    // End of variables declaration//GEN-END:variables
}
