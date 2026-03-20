package com.hospital.service;

import com.hospital.dto.MedicalRecordRequest;
import com.hospital.dto.MedicalRecordResponse;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.model.Doctor;
import com.hospital.model.MedicalRecord;
import com.hospital.model.Patient;
import com.hospital.repository.MedicalRecordRepository;
import com.hospital.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicalRecordService {

    private static final Logger log = LoggerFactory.getLogger(MedicalRecordService.class);
    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final DoctorService doctorService;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository,
                                  PatientRepository patientRepository,
                                  DoctorService doctorService) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
        this.doctorService = doctorService;
    }

    public MedicalRecordResponse createRecord(MedicalRecordRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + request.getPatientId()));
        Doctor doctor = doctorService.findDoctorById(request.getDoctorId());

        MedicalRecord record = new MedicalRecord();
        record.setPatient(patient);
        record.setDoctor(doctor);
        record.setVisitDate(request.getVisitDate());
        record.setDiagnosis(request.getDiagnosis());
        record.setPrescription(request.getPrescription());
        record.setSymptoms(request.getSymptoms());
        record.setTreatment(request.getTreatment());
        record.setLabTests(request.getLabTests());
        record.setLabResults(request.getLabResults());
        record.setWeight(request.getWeight());
        record.setHeight(request.getHeight());
        record.setBloodPressure(request.getBloodPressure());
        record.setHeartRate(request.getHeartRate());
        record.setTemperature(request.getTemperature());
        record.setFollowUpNotes(request.getFollowUpNotes());
        record.setFollowUpDate(request.getFollowUpDate());

        MedicalRecord saved = medicalRecordRepository.save(record);
        log.info("Created medical record ID: {} for patient: {}", saved.getId(), patient.getFullName());
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public MedicalRecordResponse getRecordById(Long id) {
        return mapToResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public List<MedicalRecordResponse> getRecordsByPatient(Long patientId) {
        return medicalRecordRepository.findByPatientIdOrderByVisitDateDesc(patientId).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MedicalRecordResponse> getRecordsByDoctor(Long doctorId) {
        return medicalRecordRepository.findByDoctorId(doctorId).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    public MedicalRecordResponse updateRecord(Long id, MedicalRecordRequest request) {
        MedicalRecord record = findById(id);
        record.setDiagnosis(request.getDiagnosis());
        record.setPrescription(request.getPrescription());
        record.setSymptoms(request.getSymptoms());
        record.setTreatment(request.getTreatment());
        record.setLabTests(request.getLabTests());
        record.setLabResults(request.getLabResults());
        record.setWeight(request.getWeight());
        record.setHeight(request.getHeight());
        record.setBloodPressure(request.getBloodPressure());
        record.setHeartRate(request.getHeartRate());
        record.setTemperature(request.getTemperature());
        record.setFollowUpNotes(request.getFollowUpNotes());
        record.setFollowUpDate(request.getFollowUpDate());
        return mapToResponse(medicalRecordRepository.save(record));
    }

    public void deleteRecord(Long id) {
        MedicalRecord record = findById(id);
        medicalRecordRepository.delete(record);
    }

    private MedicalRecord findById(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record not found with ID: " + id));
    }

    private MedicalRecordResponse mapToResponse(MedicalRecord r) {
        MedicalRecordResponse res = new MedicalRecordResponse();
        res.setId(r.getId());
        res.setPatientId(r.getPatient().getId());
        res.setPatientName(r.getPatient().getFullName());
        res.setDoctorId(r.getDoctor().getId());
        res.setDoctorName(r.getDoctor().getFullName());
        res.setVisitDate(r.getVisitDate());
        res.setDiagnosis(r.getDiagnosis());
        res.setPrescription(r.getPrescription());
        res.setSymptoms(r.getSymptoms());
        res.setTreatment(r.getTreatment());
        res.setLabTests(r.getLabTests());
        res.setLabResults(r.getLabResults());
        res.setWeight(r.getWeight());
        res.setHeight(r.getHeight());
        res.setBloodPressure(r.getBloodPressure());
        res.setHeartRate(r.getHeartRate());
        res.setTemperature(r.getTemperature());
        res.setFollowUpNotes(r.getFollowUpNotes());
        res.setFollowUpDate(r.getFollowUpDate());
        res.setCreatedAt(r.getCreatedAt());
        return res;
    }
}
