import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

	Connection conn = null;

	@Override
	public Connection connect(String urlConexao) {
		try {
			conn = DriverManager.getConnection(urlConexao);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		return conn;
	}

	@Override
	public void createTable(String urlConexao) {
		String sql = "CREATE TABLE IF NOT EXISTS Clientes (" + "id integer PRIMARY KEY AUTO_INCREMENT ,"
				+ "nome VARCHAR(100) NOT NULL," + "idade INTEGER NOT NULL," + "CPF VARCHAR(15) NOT NULL,"
				+ "RG VARCHAR(20) NOT NULL)";
		try {
			connect(urlConexao);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void insert(String url_conexao, Cliente cliente) {

		String sql = "INSERT INTO Clientes (nome, idade, CPF, RG) VALUES (?,?,?,?)";
		try {
			connect(url_conexao);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, cliente.getNome());
			stmt.setInt(2, cliente.getIdade());
			stmt.setString(3, cliente.getCpf());
			stmt.setString(4, cliente.getRG());

			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void selectAll(String urlConexao) {
		String sql = "SELECT * FROM Clientes";
		List<Cliente> clientes = new ArrayList<>();

		try {
			connect(urlConexao);
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				Cliente cliente = new Cliente();
				cliente.setNome(result.getString("nome"));
				cliente.setIdade(result.getInt("idade"));
				cliente.setCpf(result.getString("CPF"));
				cliente.setRG(result.getString("RG"));

				clientes.add(cliente);
			}
			result.close();
			stmt.close();

			clientes.forEach(System.out::println);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(String urlConexao, int id, String name, Integer idade) {
		String sql = "UPDATE Clientes SET nome =?, idade = ? where id = ?";
		try {
			connect(urlConexao);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setInt(2, idade);
			stmt.setInt(3, id);

			stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void delete(String urlConexao, int id) {
		String sql = "DELETE FROM Clientes where id = ?";

		try {
			connect(urlConexao);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
