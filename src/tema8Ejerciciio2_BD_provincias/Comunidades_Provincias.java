package tema8Ejerciciio2_BD_provincias;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 * @author FERNANDO ROMERO DE ÁVILA - 1º DAW 2022-23
 */
public class Comunidades_Provincias {

    private JFrame frmComuProvi;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		try {
		    Comunidades_Provincias window = new Comunidades_Provincias();
		    window.frmComuProvi.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public Comunidades_Provincias() {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    @SuppressWarnings("unchecked")
    private void initialize() {

	frmComuProvi = new JFrame();
	frmComuProvi.setFont(new Font("Dialog", Font.PLAIN, 18));
	frmComuProvi.setTitle("Tema 8 - Ejercicio 2");
	frmComuProvi.setBounds(100, 100, 500, 300);
	frmComuProvi.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frmComuProvi.getContentPane().setLayout(null);

	JLabel lblComu = new JLabel("Comunidad Autónoma:");
	lblComu.setFont(new Font("Silom", Font.PLAIN, 14));
	lblComu.setBounds(51, 49, 185, 16);
	frmComuProvi.getContentPane().add(lblComu);

	JLabel lblProv = new JLabel("Provincia:");
	lblProv.setFont(new Font("Silom", Font.PLAIN, 14));
	lblProv.setBounds(271, 49, 96, 16);
	frmComuProvi.getContentPane().add(lblProv);

	JComboBox cBoxComu = new JComboBox();
	cBoxComu.setBounds(51, 77, 162, 27);
	frmComuProvi.getContentPane().add(cBoxComu);

	JComboBox cBoxProvi = new JComboBox();
	cBoxProvi.setBounds(271, 77, 170, 27);
	frmComuProvi.getContentPane().add(cBoxProvi);

	String url = "jdbc:mysql://127.0.0.1:3306/comunidad_provincia";
	String user = "root";
	String password = "Solana12023";

	try {

	    int idp;
	    int idc;
	    int codc;
	    String nomp;
	    String nomc;

	    Connection con = DriverManager.getConnection(url, user, password);
	    Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery("SELECT nomc FROM comunidad");

	    while (rs.next()) {

		nomc = rs.getString("nomc");
		cBoxComu.addItem(nomc);

	    }
	    con.close();
	    stmt.close();
	    rs.close();

	    Connection con2 = DriverManager.getConnection(url, user, password);
	    Statement stmt2 = con2.createStatement();
	    ResultSet rs2 = stmt2.executeQuery("SELECT nomp FROM provincia WHERE codc=1");

	    while (rs2.next()) {
		nomp = rs2.getString("nomp");
		cBoxProvi.addItem(nomp);
	    }
	    con2.close();
	    stmt2.close();
	    rs2.close();

	} catch (SQLException e1) {
	    e1.printStackTrace();
	}

	cBoxComu.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {

		try {

		    int idp;
		    int idc;
		    int selectCombo = 0;
		    String nomp;
		    String nomc;

		    Connection con = DriverManager.getConnection(url, user, password);
		    selectCombo = cBoxComu.getSelectedIndex();
		    PreparedStatement sel_pstmt = con.prepareStatement("SELECT nomp FROM provincia WHERE codc=?");
		    sel_pstmt.setInt(1, selectCombo + 1);
		    ResultSet rs_sel = sel_pstmt.executeQuery();

		    cBoxProvi.removeAllItems();

		    while (rs_sel.next()) {
			nomp = rs_sel.getString("nomp");
			cBoxProvi.addItem(nomp);
		    }
		    sel_pstmt.close();
		    rs_sel.close();
		    con.close();

		} catch (SQLException e1) {
		    e1.printStackTrace();
		}

	    }
	});

    }
}

