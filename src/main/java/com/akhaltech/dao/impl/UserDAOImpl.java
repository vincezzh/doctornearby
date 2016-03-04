package com.akhaltech.dao.impl;

import com.akhaltech.dao.UserDAO;
import com.akhaltech.model.*;
import com.akhaltech.util.QueryUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Created by vzhang on 03/03/2016.
 */
@Repository
public class UserDAOImpl extends NamedParameterJdbcDaoSupport implements UserDAO {

    @Autowired
    public UserDAOImpl(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public Bookmark getBookmark(String userId, String doctorId) {
        final String sql = QueryUtil.getQuery("user", "getBookmark");
        List<Bookmark> bookmarkList = getJdbcTemplate().query(sql, new BookmarkRowMapper(), userId, doctorId);
        Bookmark bookmark = null;
        if(!CollectionUtils.isEmpty(bookmarkList)) {
            bookmark = bookmarkList.get(0);
        }

        return bookmark;
    }

    @Override
    public List<Bookmark> getBookmarks(String userId) {
        final String sql = QueryUtil.getQuery("user", "getBookmarks");
        List<Bookmark> bookmarkList = getJdbcTemplate().query(sql, new BookmarkRowMapper(), userId);

        return bookmarkList;
    }

    @Override
    public void addBookmark(final String userId, final String doctorId, final String province) {
        final String sql = QueryUtil.getQuery("user", "addBookmark");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(
            new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    pst.setString(1, userId);
                    pst.setString(2, doctorId);
                    pst.setString(3, province);
                    return pst;
                }
            },
            keyHolder
        );
    }

    @Override
    public void deleteBookmark(String userId, String doctorId, String province) {
        final String sql = QueryUtil.getQuery("user", "deleteBookmark");
        getJdbcTemplate().update(sql, new Object[] {userId, doctorId, province});
    }

    @Override
    public List<Medicine> getMedicines(String userId) {
        final String sql = QueryUtil.getQuery("user", "getMedicines");
        List<Medicine> medicineList = getJdbcTemplate().query(sql, new MedicineRowMapper(), userId);

        return medicineList;
    }

    @Override
    public void addMedicine(final Medicine medicine) {
        final String sql = QueryUtil.getQuery("user", "addMedicine");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(
            new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    pst.setString(1, medicine.getUserId());
                    pst.setString(2, medicine.getName());
                    pst.setInt(3, medicine.getPeriodMinutes());
                    pst.setString(4, medicine.getStartTime());
                    pst.setString(5, medicine.getDeviceToken());
                    return pst;
                }
            },
            keyHolder
        );
    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        final String sql = QueryUtil.getQuery("user", "deleteMedicine");
        getJdbcTemplate().update(sql, new Object[] {Long.parseLong(medicine.get_id().get$oid())});
    }

    @Override
    public List<Medicine> getMedicines() {
        return null;
    }

}

class BookmarkRowMapper implements RowMapper<Bookmark> {
    public Bookmark mapRow(ResultSet rs, int row) throws SQLException {
        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(rs.getString("user_id"));
        bookmark.setDoctorId(rs.getString("doctor_id"));
        bookmark.setProvince(rs.getString("province"));

        return bookmark;
    }
}

class MedicineRowMapper implements RowMapper<Medicine> {
    public Medicine mapRow(ResultSet rs, int row) throws SQLException {
        Medicine medicine = new Medicine();
        ID id = new ID();
        id.set$oid(String.valueOf(rs.getLong("id")));
        medicine.set_id(id);
        medicine.setUserId(rs.getString("user_id"));
        medicine.setName(rs.getString("name"));
        medicine.setPeriodMinutes(rs.getInt("period_minutes"));
        medicine.setStartTime(rs.getString("start_time"));
        medicine.setLeftMinutes(rs.getInt("left_minutes"));
        medicine.setDeviceToken(rs.getString("device_token"));

        return medicine;
    }
}