package org.onosproject.hello;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;

import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.net.Host;
import org.onosproject.net.HostId;
import org.onosproject.net.PortNumber;
import org.onosproject.net.flow.DefaultTrafficSelector;
import org.onosproject.net.flow.DefaultTrafficTreatment;
import org.onosproject.net.flow.TrafficSelector;
import org.onosproject.net.flow.TrafficTreatment;
import org.onosproject.net.host.HostService;
import org.onosproject.net.intent.HostToHostIntent;
import org.onosproject.net.intent.IntentService;
import org.onosproject.net.packet.DefaultOutboundPacket;
import org.onosproject.net.packet.InboundPacket;
import org.onosproject.net.packet.OutboundPacket;
import org.onosproject.net.packet.PacketContext;
import org.onosproject.net.packet.PacketPriority;
import org.onosproject.net.packet.PacketProcessor;
import org.onosproject.net.packet.PacketService;
import org.onosproject.net.topology.TopologyService;
import org.onlab.packet.Ethernet;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Sample reactive forwarding application.
 */
@Component(immediate = true)
public class Hello {

    // for verbose output
    private final Logger log = getLogger(getClass());

    // a list of our dependencies :
    // to register with ONOS as an application - described next
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected CoreService coreService;

    // topology information
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected TopologyService topologyService;

    // to receive Packet-in events that we'll respond to
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected PacketService packetService;

    // to submit/withdraw intents for traffic manipulation
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected IntentService intentService;

    // end host information
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected HostService hostService;

    // our application-specific event handler
    private ReactivePacketProcessor processor = new ReactivePacketProcessor();

    // our unique identifier
    private ApplicationId appId;

    // method called at startup
    @Activate
    public void activate() {
        // "org.onlab.onos.ifwd" is the FQDN of our app
        appId = coreService.registerApplication("org.onosproject.hello");
        // register our event handler
        packetService.addProcessor(processor, PacketProcessor.ADVISOR_MAX + 2);

        TrafficSelector.Builder selector = DefaultTrafficSelector.builder();
        //TrafficSelector.Builder selector2 = DefaultTrafficSelector.builder();

        //datapath_join event
        selector.matchEthType(Ethernet.TYPE_IPV4);

        packetService.requestPackets(selector.build(), PacketPriority.REACTIVE, appId);

        log.info("SSSSSSSSSSSSSSSSSStarteddddddddddddd");
        //System.out.println("SSSSSSSSSSSSSSSSSstarteddddddddddddd");
    }

    // method called at shutdown
    @Deactivate
    public void deactivate() {
        // deregister and null our handler
        packetService.removeProcessor(processor);
        processor = null;
        log.info("SSSSSSSSStoppedddddddddddddd");
        //System.out.println("SSSSSSSSSSStoppeddddddddddddd");
    }

    // our handler defined as a private inner class
    /**
     * Packet processor responsible for forwarding packets along their paths.
     */
    private class ReactivePacketProcessor implements PacketProcessor {
        @Override
        public void process(PacketContext context) {
            // Stop processing if the packet has been handled, since we
            // can't do any more to it.
            //System.out.println("Hello a!");
            if (context.isHandled()) {
                return;
            }

            //System.out.println("Hello b!");
            // Extract the original Ethernet frame from the packet information
            InboundPacket pkt = context.inPacket();
            Ethernet ethPkt = pkt.parsed();

            // Find and source and destination hosts
            HostId srcId = HostId.hostId(ethPkt.getSourceMAC());
            HostId dstId = HostId.hostId(ethPkt.getDestinationMAC());

            // Do we know who this is for? If not, flood and bail.
            Host dst = hostService.getHost(dstId);
            if (dst == null) {
                flood(context);
                return;
            }

            // Otherwise forward and be done with it.
            setUpConnectivity(context, srcId, dstId);
            forwardPacketToDst(context, dst);
        }
    }

    // Floods the specified packet if permissible.
    private void flood(PacketContext context) {
        if (topologyService.isBroadcastPoint(topologyService.currentTopology(),
                                         context.inPacket().receivedFrom())) {
            packetOut(context, PortNumber.FLOOD);
        } else {
            context.block();
        }
    }

    // Sends a packet out the specified port.
    private void packetOut(PacketContext context, PortNumber portNumber) {
        context.treatmentBuilder().setOutput(portNumber);
        context.send();
    }

    private void forwardPacketToDst(PacketContext context, Host dst) {
        TrafficTreatment treatment = DefaultTrafficTreatment.builder().setOutput(dst.location().port()).build();
        OutboundPacket packet = new DefaultOutboundPacket(dst.location().deviceId(),
                                                      treatment, context.inPacket().unparsed());
        packetService.emit(packet);
        log.info("ssssssssssssssendinggggg packet: {}", packet);
        //System.out.print("sendinggggg packet: ");
        //System.out.println(packet);
    }

    // Install a rule forwarding the packet to the specified port.
    private void setUpConnectivity(PacketContext context, HostId srcId, HostId dstId) {
        TrafficSelector selector = DefaultTrafficSelector.builder().build();
        TrafficTreatment treatment = DefaultTrafficTreatment.builder().build();
        HostToHostIntent intent = HostToHostIntent.builder()
        .appId(appId).one(srcId).two(dstId).selector(selector).treatment(treatment).build();
        intentService.submit(intent);
    }
}
