package com.chat.home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

/**
 * @author 作者 魏迎宾 E-mail weiyingbin2017@outlook.com
 * @date 创建时间：2018年2月15日 下午10:32:34
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
// VS4E -- DO NOT REMOVE THIS LINE!
public class Home extends JFrame {

	private static final long serialVersionUID = 1L;

	private static JList<String> list;

	public Home(String name, Socket client) {
		setResizable(false);
		setTitle("欢迎:" + name);
		setDefaultCloseOperation(Home.EXIT_ON_CLOSE);
		setBounds(300, 150, 686, 461);

		JPanel jPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(new ImageIcon("images\\chatHome.png").getImage(),
						0, 0, getWidth(), getHeight(), null);
			}
		};
		setContentPane(jPanel);
		jPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 380, 240);
		getContentPane().add(scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);

		new Thread() {
			public void run() {
				while (true) {
					try {
						String str = new BufferedReader(new InputStreamReader(
								client.getInputStream())).readLine();
						textArea.append(str + "\n");
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			};
		}.start();

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 270, 380, 105);
		getContentPane().add(scrollPane_1);

		final JTextArea textArea_1 = new JTextArea();
		textArea_1.setLineWrap(true);
		textArea_1.setWrapStyleWord(true);
		scrollPane_1.setViewportView(textArea_1);

		list = new JList<String>();
		list.setPreferredSize(new Dimension(200, 100));
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setListData(new String[] { "123", "weiyingbin" });
		list.setBorder(BorderFactory.createTitledBorder(null, "<" + name + ">"
				+ "在线人数", TitledBorder.LEADING, TitledBorder.TOP, new Font(
				"sdf", Font.BOLD, 20), Color.green));
		list.setSelectedIndex(0);
		new Thread(){
			public void run() {
				
			};
		}.start();

		JScrollPane scrollPane_2 = new JScrollPane(list);
		scrollPane_2.setBounds(410, 10, 245, 350);
		scrollPane_2.setOpaque(false);
		scrollPane_2.getViewport().setOpaque(false);
		getContentPane().add(scrollPane_2);

		final JButton btnNewButton_1 = new JButton();
		btnNewButton_1.setText("发送");
		btnNewButton_1.setBounds(150, 390, 60, 30);
		getContentPane().add(btnNewButton_1);

		final JButton btnNewButton_2 = new JButton();
		btnNewButton_2.setText("关闭");
		btnNewButton_2.setBounds(320, 390, 60, 30);
		getContentPane().add(btnNewButton_2);

		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String msg = textArea_1.getText();
				try {
					new PrintStream(client.getOutputStream()).println(msg);
					textArea_1.setText("");
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				int result = JOptionPane.showConfirmDialog(null, "确认退出?", "确认",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});
	}
}
