package dao;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import models.Reimbursement;
import utils.ConnectionUtil;

public class ReimbursementDao {
	public List<Reimbursement> getAllReimbs() {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ers_reimbursement";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			// Extract all reimbursements and add to list
			List<Reimbursement> reimbs = new ArrayList<>();
			while (rs.next()) {
				Reimbursement reimbursement = extractReimb(rs);
				reimbs.add(reimbursement);
			}
			return reimbs;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Reimbursement getReimb() {
		return null;
	}

	public int createReimb(Reimbursement reimb) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO ers_reimbursement "
					+ "(reimb_amount, reimb_submitted, reimb_description, reimb_receipt, reimb_author, reimb_status_id, reimb_type_id)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBigDecimal(1, reimb.getAmount());
			ps.setObject(2, LocalDateTime.now());
			ps.setString(3, reimb.getDescription());
			ps.setObject(4, reimb.getReceipt());
			ps.setInt(5, reimb.getAuthorId());
			ps.setInt(6, reimb.getReimbStatusId());
			ps.setInt(7, reimb.getReimbTypeId());

			int createCount = ps.executeUpdate();
			return createCount;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public Reimbursement updateReimb(int reimbId, int resolverId, int statusId) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE ers_reimbursement "
					+ "SET resoved = ?, reimb_resolver = ?, reimb_status_id = ? WHERE reimb_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setObject(1, LocalDateTime.now());
			ps.setInt(2, resolverId);
			ps.setInt(3, statusId);
			ps.setInt(4, reimbId);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Reimbursement reimb = extractReimb(rs);
				return reimb;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public int deleteReimb(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM ers_reimbursement WHERE reimb_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			int deleteCount = ps.executeUpdate();
			return deleteCount;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public Reimbursement extractReimb(ResultSet rs) throws SQLException {
		int id = rs.getInt("reimb_id");
		BigDecimal amount = rs.getBigDecimal("reimb_amount");
		LocalDateTime submitted = (LocalDateTime) rs.getObject("reimb_submitted");
		LocalDateTime resolved = (LocalDateTime) rs.getObject("reimb_resolved");
		String description = rs.getString("description");
		Blob receipt = (Blob) rs.getObject("reimb_receipt");
		int authorId = rs.getInt("reimb_author");
		int resolverId = rs.getInt("reimb_resolver");
		int statusId = rs.getInt("reimb_status_id");
		int reimbTypeId = rs.getInt("reimb_type_id");

		Reimbursement reimb = new Reimbursement(id, amount, submitted, resolved, description, receipt, authorId,
				resolverId, reimbTypeId, statusId);
		return reimb;
	}
}
