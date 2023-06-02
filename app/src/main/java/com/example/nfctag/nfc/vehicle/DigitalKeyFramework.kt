package com.example.nfctag.nfc.vehicle

import android.nfc.tech.IsoDep

class DigitalKeyFramework(private val isoDep: IsoDep, private val pairingCommand: PairingCommand) {
    /**
     * The vehicle sends the SELECT AID command to the device. The Digital Key framework AID is A000000809434343444B467631h
     * CCC-TS-101-Digital-Key-R3_1.0.0.pdf
     * 5.1.1 SELECT Command
     * command: 00 A4 04 00 Lc [Digital_Key_Framework_AID] 00
     * response: [Table 5-3]90 00
     * */
    fun selectAID(): ByteArray {
        val command = pairingCommand.selectAIDCmd()
        return isoDep.transceive(command.getBytes())
    }

    /**
     * selected SPAKE2+ version, all supported Digital Key applet protocol versions, and the Scrypt parameters of the SPAKE2+ protocol.
     * The vehicle shall retrieve the curve point X of the SPAKE2+ protocol in the response. The parameters used in the SPAKE2+ REQUEST
     * command are described in Section 18.
     * CCC-TS-101-Digital-Key-R3_1.0.0.pdf
     * 5.1.2 SPAKE2+ REQUEST Command
     * command: 80 30 00 00 Lc [Table 5-4] 00
     * response: [Table 5-5] 90 00
     * */
    fun spake2AndRequest(): ByteArray {
        val command = pairingCommand.spake2AndRequestCmd()
        return isoDep.transceive(command.getBytes())
    }

    /**
     * This command mutually exchanges evidence to prove that the calculated shared secret is equal on both sides.
     * CCC-TS-101-Digital-Key-R3_1.0.0.pdf
     * 5.1.3 SPAKE2+ VERIFY Command
     * command: 80 32 00 00 Lc [Table 5-7] 00
     * response: [Table 5-8] 90 00
     * */
    fun spake2AndVerify(): ByteArray {
        val command = pairingCommand.spake2AndVerifyCmd()
        return isoDep.transceive(command.getBytes())
    }

    /**
     * This command sends all data needed to generate the Digital Key to the device.
     * It is also used to provide an attestation from the vehicle of the device public key (PK) for key tracking purposes.
     * CCC-TS-101-Digital-Key-R3_1.0.0.pdf
     * 5.1.4 WriteData
     * command: 84 D4 P1 00 Lc [Table 5-7] 00
     * response: [Table 5-8] 90 00
     * Parameters P1 and P2 are always set to 00, except for the last WRITE DATA command, in which P1 shall be set to 80h.
     * */
    fun writeData(data: ByteArray): ByteArray {
        val command = pairingCommand.writeDataCmd(data)
        return isoDep.transceive(command.getBytes())
    }

    /**
     * This command sends all data needed to generate the Digital Key to the device.
     * It is also used to provide an attestation from the vehicle of the device public key (PK) for key tracking purposes.
     * CCC-TS-101-Digital-Key-R3_1.0.0.pdf
     * 5.1.4 WriteData
     * command: 84 D4 P1 00 Lc [Table 5-7] 00
     * response: [Table 5-8] 90 00
     * Parameters P1 and P2 are always set to 00, except for the last WRITE DATA command, in which P1 shall be set to 80h.
     * */
    fun writeLastData(data: ByteArray): ByteArray {
        val command = pairingCommand.writeLastDataCmd(data)
        return isoDep.transceive(command.getBytes())
    }

    /**
     * This command shall continue to use the established session keys to retrieve all data needed to
     * verify the Digital Key created by the Digital Key framework in the Digital Key applet instance.
     * CCC-TS-101-Digital-Key-R3_1.0.0.pdf
     * 5.1.5 getData
     * command: 84 CA 00 00 Lc [encrypted_tag] [command_mac] 00
     * response: [response_payload] [response_mac] 90 00 or 61XX
     * */
    fun getData(): ByteArray {
        val command = pairingCommand.getDataCmd()
        return isoDep.transceive(command.getBytes())
    }

    /**
     * This command shall only be used to retrieve remaining data that could not be fully transmitted
     * in the GET DATA response.
     * This command shall only be accepted if the GET DATA response received previously was with a status word 61XXh
     * CCC-TS-101-Digital-Key-R3_1.0.0.pdf
     * 5.1.6 getResponse
     * command: 84 C0 00 00 Lc [command_mac] 00
     * response: [response_payload] [response_mac] 90 00 or 61XX
     * */
    fun getResponse(): ByteArray {
        val command = pairingCommand.getResponseCmd()
        return isoDep.transceive(command.getBytes())
    }

    /**
     * With this command, the vehicle may control the flow at predefined points and abort the flow
     * at any time while giving a status to the device.
     * “Continue” and “End” shall be used where specified in the flow diagrams.
     * Status information about the state, successful or unsuccessful end, etc. shall be provided.
     * CCC-TS-101-Digital-Key-R3_1.0.0.pdf
     * 5.1.7 OP CONTROL FLOW Command
     * command: 80 3C [Table 5-21][Table 5-22 or Table 5-23 or Table 5-24]
     * response:  90 00
     * */
    fun opControlFlow(): ByteArray {
        val command = pairingCommand.opControlFlowCmd()
        return isoDep.transceive(command.getBytes())
    }
}