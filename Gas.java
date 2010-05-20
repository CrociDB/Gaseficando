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

public class Gas extends JFrame implements ActionListener
{
	/* Desktop Pane */
	JDesktopPane desk;
	
	/* Menu */
	JMenuBar bar;
	JMenu Menu;
	
	JMenuItem mClientes;
	JMenuItem mFuncionarios;
	JMenuItem mFornecedores;
	JMenuItem mEstoque;
	JMenuItem mVenda;
	JMenuItem mSair;
	
	/* Barras de Ferramenta e Status */
	JPanel pnlToolBar;
	
	JButton bClientes;
	JButton bFornecedores;
	JButton bFuncionarios;
	JButton bEstoque;
	JButton bVenda;
	
	JPanel pnlStatus;
	JLabel lblStatus;
	
	/* Formularios */	
	Clientes fClientes;
	Funcionarios fFuncionarios;
	Vendas fVendas;
	
	/* Conexão ao Banco de dados */
	public Connection con;
	public Statement stm;
	
	/* Login */
	private Login login;
	public int level;
	
	public Gas()
	{
		super("Gaseficando - Sistema de Gas GasZES");
		setSize(600,400);
		
		setLayout(new BorderLayout());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		Connect();
		
		addWindowListener(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					Disconnect();
				}
			}
		);
		
		BuildLayout();
		BuildForms();
		
		login = new Login(this);
	}
	
	private void Connect()
	{
		//JOptionPane.showMessageDialog(null, "Preparando para conectar...");
		
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}catch(ClassNotFoundException e){
			JOptionPane.showMessageDialog(null, "Impossível carregar o driver.");
		}
		
		try{
			con = DriverManager.getConnection("jdbc:odbc:gas");
			stm = con.createStatement();
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, "Erro ao se conectar: " + sqle.getMessage());
			System.exit(0);
		}
		
		//JOptionPane.showMessageDialog(null, "Conectado!");
	}

	private void Disconnect()
	{
		try{
			con.close();
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, "Erro ao desconectar: " + sqle.getMessage());
		}
	}
	
	private void BuildForms()
	{
		fClientes = new Clientes(this);
		desk.add(fClientes);
		
		fFuncionarios = new Funcionarios(this);
		desk.add(fFuncionarios);
		
		fVendas = new Vendas(this);
		desk.add(fVendas);
	}
	
	private void BuildLayout()
	{
		bar = new JMenuBar();
		
		Menu = new JMenu("Menu");
		bar.add(Menu);
		
		mClientes = new JMenuItem("Clientes");
		mClientes.addActionListener(this);
		
		mFuncionarios = new JMenuItem("Funcionários");
		mFuncionarios.addActionListener(this);
		
		mFornecedores = new JMenuItem("Fornecedores");
		mFornecedores.addActionListener(this);
		
		mEstoque = new JMenuItem("Estoque");
		mEstoque.addActionListener(this);
		
		mVenda = new JMenuItem("Venda");
		mVenda.addActionListener(this);
		
		mSair = new JMenuItem("Sair");
		mSair.addActionListener(this);
		
		Menu.add(mClientes);
		Menu.add(mFuncionarios);
		Menu.add(mFornecedores);
		Menu.add(mEstoque);
		Menu.add(mVenda);
		Menu.addSeparator();
		Menu.add(mSair);
		
		setJMenuBar(bar);
		
		desk = new JDesktopPane();
		desk.setBackground(Color.gray);
		getContentPane().add(desk);
		
		pnlToolBar = new JPanel();
		getContentPane().add(pnlToolBar, BorderLayout.NORTH);
		
		bClientes = new JButton("Clientes");
		bClientes.setMnemonic('C');
		bClientes.addActionListener(this);
		pnlToolBar.add(bClientes);
		
		bFuncionarios = new JButton("Funcionários");
		bFuncionarios.setMnemonic('A');
		bFuncionarios.addActionListener(this);
		pnlToolBar.add(bFuncionarios);
		
		bFornecedores = new JButton("Fornecedores");
		bFornecedores.setMnemonic('F');
		bFornecedores.addActionListener(this);
		pnlToolBar.add(bFornecedores);
		
		bEstoque = new JButton("Estoque");
		bEstoque.setMnemonic('E');
		bEstoque.addActionListener(this);
		pnlToolBar.add(bEstoque);
		
		bVenda = new JButton("Venda");
		bVenda.setMnemonic('V');
		bVenda.addActionListener(this);
		pnlToolBar.add(bVenda);
		
		pnlStatus = new JPanel();
		lblStatus = new JLabel("Faça Login para continuar...");
		//lblStatus.setBounds(0, 0, 30, 50);
		pnlStatus.add(lblStatus);
		getContentPane().add(pnlStatus, BorderLayout.SOUTH);
	}
	
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource() == mClientes || e.getSource() == bClientes)
		{
			if (!fClientes.isVisible())
			{
				fClientes.setVisible(true);
			}
		}
		
		if (e.getSource() == mFuncionarios || e.getSource() == bFuncionarios)
		{
			if (level != 0)
			{
				JOptionPane.showMessageDialog(null, "Você não tem permissão para fazer isso.",
						"Proibido", 0);
			}
			else
			{
				if (!fFuncionarios.isVisible())
				{
					fFuncionarios.setVisible(true);
				}
			}
		}
		
		if (e.getSource() == mFornecedores || e.getSource() == bFornecedores)
		{
			if (level != 0)
			{
				JOptionPane.showMessageDialog(null, "Você não tem permissão para fazer isso.",
						"Proibido", 0);
			}
		}
		
		if (e.getSource() == mEstoque || e.getSource() == bEstoque)
		{
			if (level != 0)
			{
				JOptionPane.showMessageDialog(null, "Você não tem permissão para fazer isso.",
						"Proibido", 0);
			}
		}
		
		if (e.getSource() == mVenda || e.getSource() == bVenda)
		{
			if (!fVendas.isVisible())
			{
				fVendas.setVisible(true);
			}
		}
		
		if (e.getSource() == mSair)
		{
			System.exit(0);
		}
	}
	
	public static void main(String [] args)
	{
		new Gas();
	}
}
