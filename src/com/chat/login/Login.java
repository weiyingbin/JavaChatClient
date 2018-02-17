package com.chat.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.chat.home.Home;
import com.sun.crypto.provider.DESParameters;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2018年2月15日 下午12:13:48
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
// VS4E -- DO NOT REMOVE THIS LINE!
public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	String err;

	public Login() {
		setSize(444, 330);
		JPanel jPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(new ImageIcon("images\\chatLogin.png").getImage(),
						0, 0, getWidth(), getHeight(), null);
			}
		};
		jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jPanel);
		jPanel.setLayout(null);

		final JLabel jlabel_user = new JLabel();
		jlabel_user.setText("账号:");
		jlabel_user.setFont(new Font("宋体", 0, 20));
		jlabel_user.setBounds(100, 70, 50, 20);
		jPanel.add(jlabel_user);

		final JLabel jlabel_pass = new JLabel();
		jlabel_pass.setText("密码：");
		jlabel_pass.setFont(new Font("宋体", 0, 20));
		jlabel_pass.setBounds(100, 130, 60, 20);
		jPanel.add(jlabel_pass);

		final JTextField jTextField_USER = new JTextField();
		jTextField_USER.setBounds(155, 69, 160, 24);
		jPanel.add(jTextField_USER);
		jTextField_USER.setOpaque(false);

		final JPasswordField jPasswordField_PASS = new JPasswordField();
		jPasswordField_PASS.setBounds(155, 129, 160, 24);
		jPanel.add(jPasswordField_PASS);
		jPasswordField_PASS.setOpaque(false);

		final JButton login_but = new JButton();
		login_but.setText("登录");
		login_but.setFont(new Font("微软雅黑", Font.LAYOUT_LEFT_TO_RIGHT, 12));
		login_but.setBounds(150, 200, 60, 30);
		jPanel.add(login_but);
		login_but.setContentAreaFilled(false);

		final JButton register_but = new JButton();
		register_but.setText("注册");
		register_but.setFont(new Font("微软雅黑", Font.LAYOUT_LEFT_TO_RIGHT, 12));
		register_but.setBounds(250, 200, 60, 30);
		jPanel.add(register_but);
		register_but.setContentAreaFilled(false);

		final JLabel jlable_err = new JLabel();
		jlable_err.setForeground(Color.red);
		jlable_err.setBounds(155, 155, 250, 20);
		getContentPane().add(jlable_err);

		login_but.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根

				String username = jTextField_USER.getText().trim();
				String password = new String(jPasswordField_PASS.getPassword());
				if (username.length() != 0) {
					if (password.length() != 0) {
						err = "正在连接服务器...";
						new Thread() {
							public void run() {
								while (true) {
									jlable_err.setForeground(Color.black);
									jlable_err.setText(err);
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										// TODO 自动生成的 catch 块
										e.printStackTrace();
									}
									if (err.equals("登陆成功！")) {
										break;
									}
								}
								dispose();

							};
						}.start();
						try {
							Socket login = new Socket("127.0.0.1", 8888);
							err = "服务器连接成功，正在验证账号...";
							new PrintStream(login.getOutputStream())
									.println(username + "#" + password);
							new Thread() {
								public void run() {
									try {
										String str = new BufferedReader(
												new InputStreamReader(login
														.getInputStream()))
												.readLine();
										System.out.println(str);
										if (str.equals("true")) {
											err = "登陆成功！";
											Home home = new Home(username,
													login);
											home.setVisible(true);
											setVisible(false);
										} else {
											err = "用户名或密码错误！";
											jTextField_USER.setText("");
											jPasswordField_PASS.setText("");
										}
									} catch (IOException e) {
										// TODO 自动生成的 catch 块
										e.printStackTrace();
									}
								};
							}.start();
						} catch (UnknownHostException e1) {
							// TODO 自动生成的 catch 块
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							err = "服务器连接失败，请检查网络设置！";
						}
					} else {
						jlable_err.setText("密码不能为空！");
					}
				} else {
					jlable_err.setText("用户名不能为空！");
				}
			}
		});

	}

	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e) {
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
					+ " on this platform:" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Login frame = new Login();
				frame.setDefaultCloseOperation(Login.EXIT_ON_CLOSE);
				frame.setTitle("欢迎登录XX聊天室");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
