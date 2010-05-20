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

public class Clientes extends JInternalFrame implements ActionListener
{
	/* Banco de dados */
	Gas main;
	
	/* Layout */
	JLabel lblId;
	JTextField txtId;
	
	JLabel lblNome;
	JTextField txtNome;
	
	JLabel lblCPF;
	JTextField txtCPF;
	JButton bCPF;
	
	JLabel lblEnd;
	JTextField txtEnd;
	
	DefaultTableModel mdl;
	JTable table;
	
	/* Botões */
	JButton bNovo;
	JButton bAlterar;
	JButton bSalvar;
	JButton bConsultar;
	JButton bExcluir;
	JButton bComprar;
	
	public Clientes(Gas gas)
	{
		super("Clientes", true, true, true, true);
		setSize(540, 385);
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		setResizable(false);
		
		setLayout(null);
		
		main = gas;
		
		BuildLayout();
		BuildTable();
		BuildButtons();
	}
	
	private void BuildLayout()
	{
		/* Container da Janela */
		Container c = getContentPane();
		
		/* Campo ID */
		lblId = new JLabel("ID");
		lblId.setBounds(10, 10, 50, 20);
		c.add(lblId);
		
		txtId = new JTextField();
		txtId.setBounds(10, 30, 50, 20);
		txtId.setEditable(false);
		txtId.setHorizontalAlignment(JTextField.RIGHT);
		c.add(txtId);
		
		/* Campo Nome */
		lblNome = new JLabel("Nome");
		lblNome.setBounds(70, 10, 100, 20);
		c.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(70, 30, 300, 20);
		c.add(txtNome);
		
		/* Campo CPF */
		lblCPF = new JLabel("CPF");
		lblCPF.setBounds(380, 10, 50, 20);
		c.add(lblCPF);
		
		txtCPF = new JTextField();
		txtCPF.setBounds(380, 30, 100, 20);
		c.add(txtCPF);
		
		bCPF = new JButton("...");
		bCPF.setBounds(490, 29, 30, 20);
		bCPF.addActionListener(this);
		c.add(bCPF);
		
		/* Campo endereço */
		lblEnd = new JLabel("Endereço");
		lblEnd.setBounds(10, 55, 100, 20);
		c.add(lblEnd);
		
		txtEnd = new JTextField();
		txtEnd.setBounds(10, 75, 510, 20);
		c.add(txtEnd);
		
	}
	
	private void BuildTable()
	{
		JPanel panel = new JPanel();
		panel.setBounds(10, 110, 510, 150);
		panel.setLayout(new BorderLayout());
		
		String [] col = {"ID", "Nome", "CPF"};
		String [][] dados = {{"0", "Teste", "32132132120"}};
		
		mdl = new DefaultTableModel(dados, col);
		table = new JTable(mdl);
		table.setBounds(10, 110, 510, 150);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(table);
		
		panel.add(scroll, BorderLayout.CENTER);
		getContentPane().add(panel);
		
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(2).setMaxWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		
		
		
		RefreshTable();
	}
	
	private void BuildButtons()
	{
		Container c = getContentPane();
		
		bNovo = new JButton("Novo");
		bNovo.setBounds(10, 270, 120, 30);
		bNovo.setMnemonic('N');
		bNovo.addActionListener(this);
		
		bAlterar = new JButton("Alterar");
		bAlterar.setBounds(140, 270, 120, 30);
		bAlterar.setMnemonic('A');
		bAlterar.setEnabled(false);
		bAlterar.addActionListener(this);
		
		bSalvar = new JButton("Salvar");
		bSalvar.setBounds(270, 270, 120, 30);
		bSalvar.setMnemonic('S');
		bSalvar.setEnabled(false);
		bSalvar.addActionListener(this);
		
		bConsultar = new JButton("Consultar");
		bConsultar.setBounds(400, 270, 120, 30);
		bConsultar.setMnemonic('C');
		bConsultar.addActionListener(this);
		
		bExcluir = new JButton("Excluir");
		bExcluir.setBounds(270, 310, 120, 30);
		bExcluir.setMnemonic('E');
		bExcluir.addActionListener(this);
		
		bComprar = new JButton("Comprar");
		bComprar.setBounds(400, 310, 120, 30);
		bComprar.setMnemonic('R');
		bComprar.addActionListener(this);
		
		c.add(bNovo);
		c.add(bAlterar);
		c.add(bSalvar);
		c.add(bConsultar);
		c.add(bExcluir);
		c.add(bComprar);
	}
	
	public void RefreshTable()
	{
		String sql = "SELECT * FROM clientes ORDER BY nome";
		
		try{
			ResultSet rs = main.stm.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			
			String [] col = new String[3];
			for (int i = 0; i < 3; i++)
			{
				col[i] = rsmd.getColumnLabel(i+1);
			}
			Vector vt = new Vector();
			while (rs.next())
			{
				Object[] row = new Object[3];
				for (int i=0;i< 3;i++)
					row[i] = rs.getObject(i+1);
				vt.add(row);
			}
			Object[][] lin = (Object[][])vt.toArray(new Object[0][0]);
			mdl = new DefaultTableModel(lin,col){
				public boolean isCellEditable(int row, int column)
				{
					return false;
				}
			};
			table.setModel(mdl);
			table.getColumnModel().getColumn(0).setMaxWidth(50);
			table.getColumnModel().getColumn(2).setMaxWidth(90);
			table.getColumnModel().getColumn(2).setPreferredWidth(90);
			
			rs.close();
		}
		catch (SQLException sqle)
		{
			JOptionPane.showMessageDialog(null,"Ocorreu erro ! \nCodigo:" + sqle.getErrorCode() + "\nMensagem: " + sqle.getMessage(),"Erro",0);
		}
		catch (NullPointerException e)
		{
			JOptionPane.showMessageDialog(null, "Null Pointer..." + e.getMessage());
		}
		table.addMouseListener(new MouseAdapter(){  
			public void mouseClicked(MouseEvent e){  
				if(e.getClickCount() == 2){  
					//JOptionPane.showMessageDialog(null, "Nome: " + mdl.getValueAt(table.getSelectedRow(), 1));
					RefreshForm(table.getSelectedRow());
					bSalvar.setEnabled(false);
					bAlterar.setEnabled(true);
				}  
			}  
		});  
	}
	
	private void Excluir(int id)
	{
		String sql = "DELETE FROM clientes WHERE id = " + id;
		
		try{
			main.stm.execute(sql);
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		RefreshTable();
	}
	
	private void Alterar(int id)
	{
		String sql = "UPDATE clientes SET nome = '" + txtNome.getText() +
				"', cpf = '" + txtCPF.getText() + "', endereco = '" + txtEnd.getText() +
						"' WHERE id = " + id;
		//String sql = "UPDATE clientes SET nome='Caraca' WHERE id = " + id;
		
		try{
			main.stm.execute(sql);
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}finally{
			RefreshTable();
			JOptionPane.showMessageDialog(null, "Registro alterado com sucesso.", "Sucesso", 1);
			bAlterar.setEnabled(false);
			bSalvar.setEnabled(false);
		}
	}
	
	private void Salvar()
	{
		if (txtNome.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Preencha o campo nome.", "Erro", 0);
			return;
		}
		if (!txtCPF.getText().isEmpty())
		{
			if (!CPF.Validar(txtCPF.getText()))
			{
				JOptionPane.showMessageDialog(null, "Preencha um CPF válido.", "Erro", 0);
				return;
			}
		}
		if (txtEnd.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Preencha o campo endereço.", "Erro", 0);
			return;
		}
				
		String sql = "INSERT INTO clientes(nome, cpf, endereco) VALUES('" + txtNome.getText() +
				"', '" + txtCPF.getText() + "', '" + txtEnd.getText() + "')";
		String get = "SELECT * FROM clientes WHERE nome = '" + txtNome.getText() + "'";
		
		try{
			main.stm.execute(sql);
			ResultSet res = main.stm.executeQuery(get);
			while (res.next())
			{
				int id = res.getInt("id");
				txtId.setText("" + id);
				JOptionPane.showMessageDialog(null, "Registro Adicionado com sucesso.\nCódigo do cliente: " + id, "Sucesso", 1);
			}
			res.close();
			RefreshTable();
			bAlterar.setEnabled(true);
			bSalvar.setEnabled(false);
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	private void RefreshForm(int selected)
	{
		String sql = "SELECT * FROM clientes WHERE id = " + mdl.getValueAt(selected, 0);
		
		try{
			ResultSet rs = main.stm.executeQuery(sql);
			
			while (rs.next())
			{
				txtId.setText("" + rs.getInt(1));
				txtNome.setText(rs.getString(2));
				txtCPF.setText(rs.getString(3));
				txtEnd.setText(rs.getString(4));
			}
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bCPF)
		{
			if (!CPF.Validar(txtCPF.getText()))
			{
				JOptionPane.showMessageDialog(null, "O CPF não é válido.", "Erro", 0);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "O CPF é válido.", "CPF", 1);
			}
		}
		
		if (e.getSource() == bNovo)
		{
			txtId.setText("");
			txtNome.setText("");
			txtCPF.setText("");
			txtEnd.setText("");
			
			bSalvar.setEnabled(true);
			bAlterar.setEnabled(false);
		}
		
		if (e.getSource() == bAlterar)
		{
			Alterar(Integer.parseInt("" + mdl.getValueAt(table.getSelectedRow(), 0)));
		}
		
		if (e.getSource() == bSalvar)
		{
			Salvar();
		}
		
		if (e.getSource() == bExcluir)
		{
			//JOptionPane.showMessageDialog(null, "ID: " + mdl.getValueAt(table.getSelectedRow(), 0));
			if (JOptionPane.showConfirmDialog(null, "Deseja excluir o registro?", "Excluir", 0) == 0)
			{
				Excluir(Integer.parseInt("" + mdl.getValueAt(table.getSelectedRow(), 0)));
			}			
		}
		
		if (e.getSource() == bComprar)
		{
			main.fVendas.Compra(txtId.getText());
		}
	}
}
