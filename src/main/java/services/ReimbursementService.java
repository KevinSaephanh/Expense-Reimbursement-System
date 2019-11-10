package services;

import java.sql.Blob;
import java.util.List;

import dao.ReimbursementDao;
import models.Reimbursement;
import utils.InputUtil;

public class ReimbursementService {
	private ReimbursementDao reimbDao = new ReimbursementDao();
	
	public List<Reimbursement> getAllReimbs(int pageNum) {
		List<Reimbursement> reimbs = reimbDao.getAllReimbs(pageNum);
		return reimbs;
	}
	
	public List<Reimbursement> getUserReimbs(int id) {
		List<Reimbursement> reimbs = reimbDao.getUserReimbs(id);
		return reimbs;
	}
	
	public Reimbursement getReimb(int id) {
		Reimbursement reimb = reimbDao.getReimb(id);
		return reimb;
	}
	
	public Reimbursement createReimb(Reimbursement reimb) {
		// Validate amount input
		if (!InputUtil.isValidAmount(reimb.getAmount()))
			return null;
		
		reimb = reimbDao.createReimb(reimb);
		return reimb;
	}
	
	public Blob uploadReceipt(Blob receipt, int id) {
		Blob uploadedReceipt = reimbDao.uploadReceipt(receipt, id);
		return uploadedReceipt;
	}
	
	public Reimbursement updateReimb(Reimbursement reimb, int id) {
		reimb = reimbDao.updateReimb(reimb, id);
		return reimb;
	}
	
	public int deleteReimb(int id) {
		int deleteCount = reimbDao.deleteReimb(id);
		return deleteCount;
	}
}
