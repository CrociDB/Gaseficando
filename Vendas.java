/*
 * Gaseficando
 * 
 * Projeto do técnico - 2008
 * Grupo 2
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;

import java.sql.*;
import java.util.Vector;

public class Vendas extends JInternalFrame implements ActionListener
{
	/* Banco de dados */
	Gas main;
	
	/* Layout */
	JTextField tcId;
	JLabel lCliente;
	
	JButton bPega;
	JButton bProcura;
	
	JButton bNovo;
	JButton bFechar;
	
	public Vendas(Gas gas)
	{
		super("Vendas", true, true, true, true);
		setSize(500, 145);
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		setResizable(false);		
		setLayout(null);
		
		main = gas;
		
		BuildLayout();
	}
	
	private void BuildLayout()
	{
		/* Container da Janela */
		Container c = getContentPane();
		
		/* Constroi o layout */
		JLabel lbl = new JLabel("Código do Cliente");
		lbl.setBounds(10, 10, 100, 20);
		c.add(lbl);
		
		tcId = new JTextField();
		tcId.setBounds(10, 30, 100, 20);
		c.add(tcId);
		
		bProcura = new JButton("...");
		bProcura.setBounds(120, 25, 50, 25);
		bProcura.addActionListener(this);
		c.add(bProcura);
		
		bPega = new JButton("~>");
		bPega.setBounds(180, 25, 50, 25);
		bPega.addActionListener(this);
		c.add(bPega);
		
		lCliente = new JLabel();
		lCliente.setBounds(250, 30, 230, 20);
		lCliente.setHorizontalAlignment(JTextField.RIGHT);
		c.add(lCliente);
		
		bNovo = new JButton("Novo");
		bNovo.setBounds(10, 70, 120, 30);
		bNovo.addActionListener(this);
		bNovo.setMnemonic('N');
		c.add(bNovo);
		
		bFechar = new JButton("Fechar");
		bFechar.setBounds(360, 70, 120, 30);
		bFechar.addActionListener(this);
		bFechar.setMnemonic('F');
		c.add(bFechar);
	}
	
	private void Fechar()
	{
		if (tcId.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Coloque o código do cliente.", "Erro", 0);
			return;
		}
		
		String sql = "INSERT INTO vendas (id_cliente, preco) VALUES (" + tcId.getText() + ", 30)";
		String sql1 = "SELECT * FROM clientes WHERE id = " + tcId.getText();
		
		JTextArea relatorio = new JTextArea();
		
		try
		{
			main.stm.execute(sql);
			
			ResultSet rs = main.stm.executeQuery(sql1);
			if (rs.next())
			{
				relatorio.setSize(new Dimension(500, 400));
				relatorio.setText("                                 Nota Fiscal" +
						"\n--------------------------------------------------------------------\n\n" +
						"Nome do Cliente: " + rs.getString("nome") + "\n" +
						"Endereço: " + rs.getString("endereco") + "\n\n" +
						"1x GAS 13LR$                                            R$ 30,00" +
						"\n\n                                                   Sub-Total: R$ 30,00" +
						"\n\n-------------------------------------------------------------------\nGas ZES");
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		JOptionPane.showMessageDialog(null, "Compra realizada com sucesso. Imprimindo nota fiscal...", "Sucesso", 2);
		JOptionPane.showMessageDialog(null, relatorio);
		
		tcId.setText("");
		lCliente.setText("");
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bProcura)
		{
			main.fClientes.setVisible(false);
			main.fClientes.setVisible(true);
		}
		
		if (e.getSource() == bPega)
		{
			Mostra();
		}
		
		if (e.getSource() == bNovo)
		{
			tcId.setText("");
			lCliente.setText("");
		}
		
		if (e.getSource() == bFechar)
		{
			Fechar();
		}
	}
	
	public void Mostra()
	{
		String sql = "SELECT * FROM clientes WHERE id = " + tcId.getText();
		
		try
		{
			ResultSet rs = main.stm.executeQuery(sql);
			if (rs.next())
			{
				lCliente.setText(rs.getString("nome"));
				return;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Código de cliente inválido. Por favor, utlizize a pesquisa.",
						"Código inválido", 0);
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void Compra(String id)
	{
		setVisible(false);
		setVisible(true);
		tcId.setText(id);
		Mostra();
	}
}
