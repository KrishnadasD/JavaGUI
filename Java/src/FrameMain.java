import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;

public class FrameMain {

	private JFrame frame;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JTable table;
	private JTextField textField;

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
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				ShowData();
			}
		});
		frame.getContentPane().setBackground(UIManager.getColor("activeCaption"));
		frame.setBounds(100, 100, 997, 664);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel nlab = new JLabel("");
		nlab.setForeground(Color.RED);
		nlab.setFont(new Font("Tahoma", Font.ITALIC, 15));
		nlab.setBounds(420, 93, 351, 24);
		frame.getContentPane().add(nlab);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Calibri", Font.BOLD, 18));
		lblName.setBounds(29, 94, 67, 34);
		frame.getContentPane().add(lblName);
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setFont(new Font("Calibri", Font.BOLD, 18));
		lblDepartment.setBounds(29, 152, 136, 34);
		frame.getContentPane().add(lblDepartment);
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String PATTERN="^[a-zA-Z]+$";
				Pattern patt=Pattern.compile(PATTERN);
				Matcher match=patt.matcher(textField_1.getText());
				if(!match.matches()) {
					nlab.setText("Name should not contain numericals (0-9)");
				}
				else {
					nlab.setText(null);
				}
			}
		});
		textField_1.setFont(new Font("Calibri", Font.BOLD, 16));
		textField_1.setBounds(167, 98, 209, 27);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(20);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Calibri", Font.BOLD, 16));
		textField_2.setBounds(167, 156, 209, 27);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(20);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textField.getText().isEmpty()||textField_1.getText().isEmpty()||textField_2.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,"Enter all fields");
				}
				else{
					SaveToDatabase();
					
				}
				ShowData();
			}
			
		});
		btnNewButton.setFont(new Font("Calibri", Font.BOLD, 16));
		btnNewButton.setBounds(84, 221, 92, 34);
		frame.getContentPane().add(btnNewButton);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con=conn();
				if(textField.getText().isEmpty()||textField_1.getText().isEmpty()||textField_2.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,"Enter all fields");
				}
				else {
				try {
					String query="update student set  ID='"+textField.getText()+"',Name='"+textField_1.getText()+"', Department='"+textField_2.getText()+"'where ID='"+textField.getText()+ "'";
					PreparedStatement ps=con.prepareStatement(query);
					
					ps.execute();
					ps.close();
					con.close();
					JOptionPane.showMessageDialog(null, "Updated");
					
				}catch(Exception e1) {
					System.out.println("Error"+e1 );
				}
				}
				ShowData();
				}
		});
		btnUpdate.setFont(new Font("Calibri", Font.BOLD, 16));
		btnUpdate.setBounds(205, 221, 105, 34);
		frame.getContentPane().add(btnUpdate);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con=conn();
				if(textField.getText().isEmpty()||textField_1.getText().isEmpty()||textField_2.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,"Enter all fields");
				}
				else {
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
				ShowData();}
		});
		btnDelete.setFont(new Font("Calibri", Font.BOLD, 16));
		btnDelete.setBounds(342, 221, 92, 34);
		frame.getContentPane().add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(64, 296, 531, 299);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Calibri", Font.BOLD, 18));
		lblId.setBounds(29, 40, 67, 34);
		frame.getContentPane().add(lblId);
		
		textField = new JTextField();
		textField.setFont(new Font("Calibri", Font.BOLD, 16));
		textField.setBounds(167, 47, 209, 27);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setIcon(new ImageIcon("C:\\Users\\Kichu\\Downloads\\iconfinder_List_132709.png"));
		mntmNew.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open File");
		mntmOpenFile.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnFile.add(mntmOpenFile);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnFile.add(mntmClose);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setIcon(new ImageIcon("C:\\Users\\Kichu\\Downloads\\iconfinder_Exit_132751.png"));
		mntmExit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnEdit);
		
		JMenuItem mntmCut = new JMenuItem("Cut");
		mntmCut.setIcon(new ImageIcon("C:\\Users\\Kichu\\Downloads\\Cut-icon (1).png"));
		mntmCut.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnEdit.add(mntmCut);
		
		JMenuItem mntmCopy = new JMenuItem("Copy");
		mntmCopy.setIcon(new ImageIcon("C:\\Users\\Kichu\\Downloads\\iconfinder_Copy_132650.png"));
		mntmCopy.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnEdit.add(mntmCopy);
		
		JSeparator separator_2 = new JSeparator();
		mnEdit.add(separator_2);
		
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.setIcon(new ImageIcon("C:\\Users\\Kichu\\Downloads\\iconfinder_Delete_132746.png"));
		mntmDelete.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnEdit.add(mntmDelete);
		
		JMenu mnView = new JMenu("View");
		mnView.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnView);
		
		JMenu mnZoom = new JMenu("Zoom");
		mnZoom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnView.add(mnZoom);
		
		JMenuItem mntmZoomIn = new JMenuItem("Zoom In");
		mntmZoomIn.setIcon(new ImageIcon("C:\\Users\\Kichu\\Downloads\\Zoom-In-icon.png"));
		mntmZoomIn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnZoom.add(mntmZoomIn);
		
		JMenuItem mntmZoomOut = new JMenuItem("Zoom Out");
		mntmZoomOut.setIcon(new ImageIcon("C:\\Users\\Kichu\\Downloads\\Zoom-Out-icon.png"));
		mntmZoomOut.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnZoom.add(mntmZoomOut);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnHelp);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("View Help");
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnHelp.add(mntmNewMenuItem);
		
		JMenuItem mntmSendFeedback = new JMenuItem("Send Feedback");
		mntmSendFeedback.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnHelp.add(mntmSendFeedback);
		
		JSeparator separator_3 = new JSeparator();
		mnHelp.add(separator_3);
		
		JMenuItem mntmAboutApp = new JMenuItem("About App");
		mntmAboutApp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnHelp.add(mntmAboutApp);
		
		
		
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
	public void SaveToDatabase() {
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
	public void ShowData() {
		Connection con=conn();
		DefaultTableModel model=new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Name");
		model.addColumn("Department");
		try {
			String query="select * from student";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(query);
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("ID"),
						rs.getString("Name"),
						rs.getString("Department")
				
			});
			}
			
			rs.close();
			st.close();
			con.close();
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(50);
			table.getColumnModel().getColumn(0).setPreferredWidth(50);
			table.getColumnModel().getColumn(1).setPreferredWidth(50);
		}catch(Exception e) {
			System.out.println("Error" + e);
		}
	}
}
