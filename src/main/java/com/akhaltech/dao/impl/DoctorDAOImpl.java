package com.akhaltech.dao.impl;

import com.akhaltech.dao.DoctorDAO;
import com.akhaltech.model.*;
import com.akhaltech.util.QueryUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vzhang on 03/03/2016.
 */
@Repository
public class DoctorDAOImpl extends NamedParameterJdbcDaoSupport implements DoctorDAO {

    private final static Logger log = Logger.getLogger(DoctorDAOImpl.class);

    @Autowired
    public DoctorDAOImpl(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public List<Doctor> search(DoctorSearch search) {
        log.info("DoctorDAOImpl: search() ----> Start");
        final String sql = QueryUtil.getQuery("doctor", "search") + search.getSearchConditionsSQL() + " ON zz._id=d._id ORDER BY p.surname, p.given_name ASC LIMIT ?, ?";
        log.info(sql);
        List<Doctor> doctorList = getJdbcTemplate().query(sql, new SimpleDoctorRowMapper(), search.getSkip(), search.getLimit());
        addSpecialtyListToDoctors(doctorList);

        log.info("DoctorDAOImpl: search() ----> End");
        return doctorList;
    }

    @Override
    public List<Doctor> getDoctors(List<String> idList) {
        StringBuffer conditionSB = new StringBuffer();
        for(String id : idList) {
            if(conditionSB.length() == 0) {
                conditionSB.append(" ('");
                conditionSB.append(id);
                conditionSB.append("' ");
            }else {
                conditionSB.append(", '");
                conditionSB.append(id);
                conditionSB.append("' ");
            }
        }
        conditionSB.append(" ) ");
        final String sql = QueryUtil.getQuery("doctor", "getSimpleDoctors") + conditionSB.toString() + " ORDER BY p.surname, p.given_name ASC";
        List<Doctor> doctorList = getJdbcTemplate().query(sql, new SimpleDoctorRowMapper());
        addSpecialtyListToDoctors(doctorList);

        return doctorList;
    }

    private void addSpecialtyListToDoctors(List<Doctor> doctorList) {
        if(doctorList != null && doctorList.size() > 0) {
            StringBuffer subConditionSB = new StringBuffer();
            subConditionSB.append(" ( ");
            for(int i=0; i<doctorList.size(); i++) {
                if(i > 0) {
                    subConditionSB.append(",");
                }
                subConditionSB.append("'" + doctorList.get(i).get_id() + "'");
            }
            subConditionSB.append(" ) ");
            final String subSql = QueryUtil.getQuery("doctor", "getSpecialtyByDoctorIdList") + subConditionSB.toString();
            List<Specialty> specialtyList = getJdbcTemplate().query(subSql, new SpecialtyRowMapper());
            if(specialtyList != null && specialtyList.size() > 0) {
                for(Specialty specialty : specialtyList) {
                    for(Doctor doctor : doctorList) {
                        if(doctor.get_id().equals(specialty.getDoctorId())) {
                            if(doctor.getSpecialtyList() == null) {
                                doctor.setSpecialtyList(new ArrayList<Specialty>());
                            }
                            doctor.getSpecialtyList().add(specialty);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Doctor getDoctorById(String id) {
        return getDoctorDetail(id);
    }

    private Doctor getDoctorDetail(String id) {
        final String sql = QueryUtil.getQuery("doctor", "getDoctorByDoctorId");
        List<Doctor> doctorList = getJdbcTemplate().query(sql, new DoctorRowMapper(), id);
        Doctor doctor = null;
        if(!CollectionUtils.isEmpty(doctorList)) {
            doctor = doctorList.get(0);
        }

        if(doctor != null) {
            doctor.setPrivilegeList(getPrivilegeByDoctorId(id));
            doctor.setSpecialtyList(getSpecialtyByDoctorId(id));
            if(doctor.getProfile().getId() != null) {
                doctor.setProfile(getProfileByProfileId(doctor.getProfile().getId()));
            }
            if(doctor.getLocation().getId() != null) {
                doctor.setLocation(getLocationByLocationId(doctor.getLocation().getId()));
            }
            if(doctor.getRegistration().getId() != null) {
                doctor.setRegistration(getRegistrationByRegistrationId(doctor.getRegistration().getId()));
            }
        }

        return doctor;
    }

    private List<Privilege> getPrivilegeByDoctorId(String id) {
        final String sql = QueryUtil.getQuery("doctor", "getPrivilegeByDoctorId");
        List<Privilege> privilegeList = getJdbcTemplate().query(sql, new PrivilegeRowMapper(), id);
        return privilegeList;
    }

    private List<Specialty> getSpecialtyByDoctorId(String id) {
        final String sql = QueryUtil.getQuery("doctor", "getSpecialtyByDoctorId");
        List<Specialty> specialtyList = getJdbcTemplate().query(sql, new SpecialtyRowMapper(), id);
        return specialtyList;
    }

    private Profile getProfileByProfileId(Long id) {
        final String sql = QueryUtil.getQuery("doctor", "getProfileByProfileId");
        List<Profile> profileList = getJdbcTemplate().query(sql, new ProfileRowMapper(), id);
        Profile profile = null;
        if(!CollectionUtils.isEmpty(profileList)) {
            profile = profileList.get(0);
        }

        if(profile != null) {
            profile.setLanguageList(getLanguageByProfileId(profile.getId()));
        }

        return profile;
    }

    private List<String> getLanguageByProfileId(Long profileId) {
        final String sql = QueryUtil.getQuery("doctor", "getLanguageByProfileId");
        List<String> languageList = getJdbcTemplate().query(sql, new LanguageRowMapper(), profileId);
        return languageList;
    }

    private Location getLocationByLocationId(Long locationId) {
        final String sql = QueryUtil.getQuery("doctor", "getLocationByLocationId");
        List<Location> locationList = getJdbcTemplate().query(sql, new LocationRowMapper(), locationId);
        Location location = null;
        if(!CollectionUtils.isEmpty(locationList)) {
            location = locationList.get(0);
        }

        return location;
    }

    private Registration getRegistrationByRegistrationId(Long registrationId) {
        final String sql = QueryUtil.getQuery("doctor", "getRegistrationByRegistrationId");
        List<Registration> registrationList = getJdbcTemplate().query(sql, new RegistrationRowMapper(), registrationId);
        Registration registration = null;
        if(!CollectionUtils.isEmpty(registrationList)) {
            registration = registrationList.get(0);
        }

        if(registration != null) {
            registration.setTrainingList(getPostgraduateTrainingByRegistrationId(registrationId));
            registration.setHistoryList(getRegistrationHistoryByRegistrationId(registrationId));
        }

        return registration;
    }

    private List<PostgraduateTraining> getPostgraduateTrainingByRegistrationId(Long registrationId) {
        final String sql = QueryUtil.getQuery("doctor", "getPostgraduateTrainingByRegistrationId");
        List<PostgraduateTraining> postgraduateTrainingList = getJdbcTemplate().query(sql, new PostgraduateTrainingRowMapper(), registrationId);
        return postgraduateTrainingList;
    }

    private List<RegistrationHistory> getRegistrationHistoryByRegistrationId(Long registrationId) {
        final String sql = QueryUtil.getQuery("doctor", "getRegistrationHistoryByRegistrationId");
        List<RegistrationHistory> registrationHistoryList = getJdbcTemplate().query(sql, new RegistrationHistoryRowMapper(), registrationId);
        return registrationHistoryList;
    }
}

class PostgraduateTrainingRowMapper implements RowMapper<PostgraduateTraining> {
    public PostgraduateTraining mapRow(ResultSet rs, int row) throws SQLException {
        PostgraduateTraining postgraduateTraining = new PostgraduateTraining();
        postgraduateTraining.setType(rs.getString("type"));
        postgraduateTraining.setDiscipline(rs.getString("discipline"));
        postgraduateTraining.setMedicalSchool(rs.getString("medical_school"));
        postgraduateTraining.setFrom(rs.getString("from"));
        postgraduateTraining.setTo(rs.getString("to"));

        return postgraduateTraining;
    }
}

class RegistrationHistoryRowMapper implements RowMapper<RegistrationHistory> {
    public RegistrationHistory mapRow(ResultSet rs, int row) throws SQLException {
        RegistrationHistory registrationHistory = new RegistrationHistory();
        registrationHistory.setDescription(rs.getString("description"));
        registrationHistory.setEffectiveDate(rs.getString("effectiveDate"));

        return registrationHistory;
    }
}

class RegistrationRowMapper implements RowMapper<Registration> {
    public Registration mapRow(ResultSet rs, int row) throws SQLException {
        Registration registration = new Registration();
        registration.setId(rs.getLong("id"));
        registration.setRegistrationClass(rs.getString("registration_class"));
        registration.setCertificateIssued(rs.getString("certificate_issued"));
        registration.setRegistrationStatus(rs.getString("registration_status"));
        registration.setEffectiveFrom(rs.getString("effective_from"));
        registration.setExpiryDate(rs.getString("expiry_date"));
        registration.setGraduatedFrom(rs.getString("graduated_from"));
        registration.setYearOfGraduation(rs.getString("year_of_graduation"));
        registration.setTermsAndConditions(rs.getString("terms_and_conditions"));

        return registration;
    }
}

class LocationRowMapper implements RowMapper<Location> {
    public Location mapRow(ResultSet rs, int row) throws SQLException {
        Location location = new Location();
        location.setId(rs.getLong("id"));
        location.setAddressSummary(rs.getString("address_summary"));
        location.setContactSummary(rs.getString("contact_summary"));
        location.setElectoralDistrict(rs.getString("electoral_district"));
        location.setCorporationName(rs.getString("corporation_name"));
        location.setMedicalLicensesInOtherJurisdictions(rs.getString("medical_Licenses_in_other_jurisdictions"));

        return location;
    }
}

class LanguageRowMapper implements RowMapper<String> {
    public String mapRow(ResultSet rs, int row) throws SQLException {
        return rs.getString("language");
    }
}

class ProfileRowMapper implements RowMapper<Profile> {
    public Profile mapRow(ResultSet rs, int row) throws SQLException {
        Profile profile = new Profile();
        profile.setId(rs.getLong("id"));
        profile.setGivenName(rs.getString("given_name"));
        profile.setSurname(rs.getString("surname"));
        profile.setFormerName(rs.getString("former_name"));
        profile.setGender(rs.getString("gender"));

        return profile;
    }
}

class SpecialtyRowMapper implements RowMapper<Specialty> {
    public Specialty mapRow(ResultSet rs, int row) throws SQLException {
        Specialty specialty = new Specialty();
        specialty.setName(rs.getString("name"));
        specialty.setIssueOn(rs.getString("issue_on"));
        specialty.setType(rs.getString("type"));
        specialty.setDoctorId(rs.getString("doctor_id"));

        return specialty;
    }
}

class PrivilegeRowMapper implements RowMapper<Privilege> {
    public Privilege mapRow(ResultSet rs, int row) throws SQLException {
        Privilege privilege = new Privilege();
        privilege.setHospitalDetail(rs.getString("hospital_detail"));

        return privilege;
    }
}

class SimpleDoctorRowMapper implements RowMapper<Doctor> {
    public Doctor mapRow(ResultSet rs, int row) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.set_id(rs.getString("_id"));
        String doctorId = rs.getString("_id");
        doctor.setDoctorId(doctorId.substring(doctorId.indexOf("_") + 1));
        doctor.setProvince(rs.getString("province"));

        Profile profile = new Profile();
        profile.setSurname(rs.getString("surname"));
        profile.setGivenName(rs.getString("given_name"));
        doctor.setProfile(profile);

        Location location = new Location();
        location.setAddressSummary(rs.getString("address_summary"));
        location.setContactSummary(rs.getString("contact_summary"));
        doctor.setLocation(location);

        return doctor;
    }
}

class DoctorRowMapper implements RowMapper<Doctor> {
    public Doctor mapRow(ResultSet rs, int row) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.set_id(rs.getString("_id"));
        String doctorId = rs.getString("_id");
        doctor.setDoctorId(doctorId.substring(doctorId.indexOf("_") + 1));
        doctor.setStatus(rs.getString("status"));
        doctor.setProvince(rs.getString("province"));
        doctor.setCountry(rs.getString("country"));
        Profile profile = new Profile();
        profile.setId(rs.getLong("profile_id"));
        doctor.setProfile(profile);
        Location location = new Location();
        location.setId(rs.getLong("location_id"));
        doctor.setLocation(location);
        Registration registration = new Registration();
        registration.setId(rs.getLong("registration_id"));
        doctor.setRegistration(registration);

        return doctor;
    }
}