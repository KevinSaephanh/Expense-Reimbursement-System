package services;

import java.util.List;

import dao.ReimbursementDao;
import models.Reimbursement;
import utils.InputUtil;

public class ReimbursementService {
	private ReimbursementDao reimbDao = new ReimbursementDao();
	
	public List<Reimbursement> getAllReimbs() {
		List<Reimbursement> reimbs = reimbDao.getAllReimbs();
		return reimbs;
	}
	
	public List<Reimbursement> getPendingReimbs() {
		List<Reimbursement> reimbs = reimbDao.getPendingReimbs();
		return reimbs;
	}
	
	public List<Reimbursement> getApprovedReimbs() {
		List<Reimbursement> reimbs = reimbDao.getApprovedReimbs();
		return reimbs;
	}
	
	public List<Reimbursement> getDeniedReimbs() {
		List<Reimbursement> reimbs = reimbDao.getDeniedReimbs();
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
	
	public int createReimb(Reimbursement reimb) {
		// Validate amount input
		if (!InputUtil.isValidAmount(reimb.getAmount()))
			return 0;
		
		int createCount = reimbDao.createReimb(reimb);
		return createCount;
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
