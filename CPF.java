/*
 * by CrociDB
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class CPF extends JFrame implements ActionListener
{
	JLabel lblTexto;
	
	JTextField txtFirst;
	JTextField txtLast;
	
	JButton btnValida;
	
	public CPF()
	{
		super("Validador de CPF");
		setLayout(null);
		setSize(new Dimension(270,140));
		
		CreateGUI();
	}
	
	private void CreateGUI()
	{
		lblTexto = new JLabel("Digite os primeiros 9 dígidos do CPF");
		lblTexto.setBounds(10, 10, 250, 20);
		getContentPane().add(lblTexto);
		
		txtFirst = new JTextField();
		txtFirst.setBounds(10, 40, 180, 20);
		getContentPane().add(txtFirst);
		
		txtLast = new JTextField();
		txtLast.setBounds(200, 40, 50, 20);
		getContentPane().add(txtLast);
		
		btnValida = new JButton("Gerar CPF");
		btnValida.setBounds(10, 70, 240, 30);
		btnValida.addActionListener(this);
		getContentPane().add(btnValida);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnValida)
		{
			if (txtFirst.getText().length() != 9)
			{
				JOptionPane.showMessageDialog(null, "Por favor, coloque os primeiros 9 dígitos do CPF (sem pontos).");
			}
			else
			{
				txtLast.setText("" + Gerar(txtFirst.getText()));
				//JOptionPane.showMessageDialog(null, "Resultado: " + Validar(txtFirst.getText() + txtLast.getText()));
			}
			//Validar("32109745860");
		}
	}
	
	static public int Gerar(String strCPF)
	{
		int al1, al2;
		
		int soma = 0;
		
		for (int i = 0; i < 9; i++)
		{
			soma += (Integer.parseInt(strCPF.substring(i, i+1))) * (10-i);
		}
		
		if ((soma % 11) >= 0 && (soma % 11) <= 1)
		{
			al1 = (soma % 11);
		}
		else
		{
			al1 = 11 - (soma % 11);
		}
		
		soma = 0;
		int i;
		for (i = 0; i < 9; i++)
		{
			soma += (Integer.parseInt(strCPF.substring(i, i+1))) * (11-i);
		}
		soma += al1 * (11-i);
		
		if ((soma % 11) < 2)
		{
			al2 = 0;
		}
		else
		{
			al2 = 11 - (soma % 11);
		}
		
		
		return Integer.parseInt("" + al1 + al2);
	}
	
	static public boolean Validar (String CPF)
	{
		if (CPF.length() != 11)
		{
			return false;
		}
		
		String a, b;
		a = CPF.substring(0, 9);
		b = CPF.substring(9, 11);
		
		if (Gerar(a) == Integer.parseInt(b))
		{
			return true;
		}		
		return false;		
	}
	
	public static void main(String [] args)
	{
		new CPF().setVisible(true);
	}
}
