package app.coronawarn.server.tools.protoplayground;

import app.coronawarn.server.tools.protoplayground.gen.Key2;
import app.coronawarn.server.tools.protoplayground.gen.SubmissionPayload;
import app.coronawarn.server.tools.protoplayground.gen.VerificationType;
import com.google.protobuf.ByteString;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SubmissionPayloadGenerator {

  public static void main(String[] args) throws IOException {
    SubmissionPayloadGenerator submissionPayloadGenerator = new SubmissionPayloadGenerator();
    submissionPayloadGenerator.getSubmissionPayload();
  }

  public void getSubmissionPayload() throws IOException {
    Key2.Builder key = Key2.newBuilder();
    key.setKeyData(ByteString.copyFrom(new byte[16]));
    key.setTransmissionRiskLevel(4);
    key.setRollingStartNumber(2660760);
    key.setRollingPeriod(144);
    key.addAllVisitedCountries(List.of("DE", "FR"));
    key.setOrigin("DE");
    key.setVerificationType(VerificationType.LAB_VERIFIED);

    SubmissionPayload.Builder submissionPayload = SubmissionPayload.newBuilder();
    submissionPayload.addKeys(key);
    submissionPayload.build()
        .writeTo(new FileOutputStream("submission_payload.bin"));
  }
}
