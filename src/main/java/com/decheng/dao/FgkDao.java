package com.decheng.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.decheng.domain.Fgk;
import com.decheng.tools.Utils;

public class FgkDao {
	
	private final static Logger logger = LoggerFactory.getLogger(FgkDao.class); 

	public void insertList(List<Fgk> list) {
		Connection conn = Utils.getConn();
		String sql = "INSERT INTO f_content(title,posting_date,reference_num,tax_kind,content) VALUES (?,?,?,?,?)";

		Fgk fgk = new Fgk();
		PreparedStatement st;
		final int batchSize = 200;
		int count = 0;
		try {
			st = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				fgk = list.get(i);
				st.setString(1, fgk.getTitle());
				st.setString(2, fgk.getPostingDate());
				st.setString(3, fgk.getReferenceNum());
				st.setString(4, fgk.getTaxKind());
				st.setString(5, fgk.getContent());
				st.addBatch();
				if (++count % batchSize == 0 || i == list.size() - 1) {
					st.executeBatch();
				}
			}
			st.close();
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		} finally {
			Utils.closeDb(conn);
		}
	}

	public void delete() {
		Connection conn = Utils.getConn();
		String sql = "TRUNCATE TABLE f_content;";
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.execute();
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		} finally {
			Utils.closeDb(conn);
		}
	}
}
