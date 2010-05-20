/*
 * Gaseficando
 * 
 * Projeto do técnico - 2008
 * Grupo 2
 */

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

import java.sql.*;

public class Login extends JFrame implements ActionListener 
{
	Gas main;
	
	JLabel lLogin;
	JLabel lSenha;
	JTextField tLogin;
	JPasswordField tSenha;
	
	JButton bLogin;
	
	public Login (Gas gas)
	{
		super("Gaseficando - Login");
		setSize(250, 250);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Centralizar();
		
		main = gas;
		//main.setVisible(false);
		BuildLayout();		
		setVisible(true);
	}
	
	private void BuildLayout()
	{
		JLabel img = new JLabel();
		img.setBounds(0, 0, 250, 50);
		img.setVerticalAlignment(JLabel.TOP);
		img.setIcon(new javax.swing.ImageIcon(getClass().getResource("img/logo.png")));
		getContentPane().add(img);
		
		lLogin = new JLabel("Usuário");
		lLogin.setBounds(10, 60, 100, 20);
		getContentPane().add(lLogin);
		
		tLogin = new JTextField();
		tLogin.setBounds(10, 80, 225, 20);
		getContentPane().add(tLogin);
		
		lSenha = new JLabel("Senha");
		lSenha.setBounds(10, 110, 100, 20);
		getContentPane().add(lSenha);
		
		tSenha = new JPasswordField();
		tSenha.setBounds(10, 130, 225, 20);
		getContentPane().add(tSenha);
		
		bLogin = new JButton("Login");
		bLogin.setBounds(70, 170, 100, 30);
		bLogin.addActionListener(this);
		getContentPane().add(bLogin);
	}
	
	public void Centralizar()
	{
		Dimension T = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension J = getSize();
		
		setLocation((T.width-J.width)/2, (T.height-J.height)/2);
	}
	
	private void Login()
	{
		String sql = "SELECT * FROM funcionarios WHERE login = '" + tLogin.getText() +
			"' AND senha = '" + tSenha.getText() + "'";
		
		try{
			ResultSet rs = main.stm.executeQuery(sql);
			if (rs.next())
			{
				setVisible(false);
				main.setVisible(true);
				main.lblStatus.setText("Bem vindo " + rs.getString("nome"));
				main.level = rs.getInt("nivel");
				return;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Usuário ou senha errados. Tente novamente.", "Erro", 0);
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bLogin)
		{
			Login();
		}
	}
}
