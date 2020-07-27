import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;



import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameMain {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameMain window = new FrameMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrameMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 888, 570);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Calibri", Font.BOLD, 18));
		lblId.setBounds(40, 36, 67, 34);
		frame.getContentPane().add(lblId);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Calibri", Font.BOLD, 18));
		lblName.setBounds(40, 83, 67, 34);
		frame.getContentPane().add(lblName);
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setFont(new Font("Calibri", Font.BOLD, 18));
		lblDepartment.setBounds(40, 143, 136, 34);
		frame.getContentPane().add(lblDepartment);
		
		textField = new JTextField();
		textField.setFont(new Font("Calibri", Font.BOLD, 18));
		textField.setBounds(169, 40, 209, 27);
		frame.getContentPane().add(textField);
		textField.setColumns(20);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Calibri", Font.BOLD, 18));
		textField_1.setBounds(169, 86, 209, 27);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(20);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Calibri", Font.BOLD, 18));
		textField_2.setBounds(171, 146, 207, 27);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(20);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveToDatabase();
			}
			
		});
		btnNewButton.setFont(new Font("Calibri", Font.BOLD, 16));
		btnNewButton.setBounds(171, 195, 92, 34);
		frame.getContentPane().add(btnNewButton);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con=conn();
				try {
					String query="update student set ID='"+textField.getText()+"', Name='"+textField_1.getText()+"', Department='"+textField_2.getText()+"'where ID='"+textField.getText()+ "'";
					PreparedStatement ps=con.prepareStatement(query);
					
					ps.execute();
					ps.close();
					con.close();
					JOptionPane.showMessageDialog(null, "Updated");
					
				}catch(Exception e1) {
					System.out.println("Error"+e1 );
				}
				
			}
		});
		btnUpdate.setFont(new Font("Calibri", Font.BOLD, 16));
		btnUpdate.setBounds(273, 195, 105, 34);
		frame.getContentPane().add(btnUpdate);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con=conn();
				try {
					String query="delete from student where ID='"+textField.getText()+"' ";
					PreparedStatement ps=con.prepareStatement(query);
					
					ps.execute();
					ps.close();
					con.close();
					JOptionPane.showMessageDialog(null, "Deleted");
					
				}catch(Exception e2) {
					System.out.println("Error"+e2 );
				}
			}
		});
		btnDelete.setFont(new Font("Calibri", Font.BOLD, 16));
		btnDelete.setBounds(388, 195, 92, 34);
		frame.getContentPane().add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 265, 551, 258);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
//		btnNewButton_1.setFont(new Font("Calibri", Font.BOLD, 16));
//		btnNewButton_1.setBounds(491, 195, 147, 34);
//		frame.getContentPane().add(btnNewButton_1);
	}
	public static Connection conn() {
		try {
			String driver="com.mysql.jdbc.Driver";
			String url="jdbc:mysql://localhost:3306/studentdatabase";
			Class.forName(driver);
			return DriverManager.getConnection(url,"root","Oneplus@7pro");
		}catch(Exception e) {
			System.out.println("Connection failed" + e);
		}
		return null;
	}
	private void SaveToDatabase() {
		Connection con=conn();
		try {
			PreparedStatement ps=con.prepareStatement("INSERT INTO student (ID,Name,Department) VALUES (?,?,?);");
			ps.setString(1,textField.getText());
			ps.setString(2,textField_1.getText());
			ps.setString(3,textField_2.getText());
			ps.execute();
			JOptionPane.showMessageDialog(null, "Saved!!!");
		}catch(Exception e) {
			System.out.println("error"+e);
		}
	}
}
