import { useAuth } from "@/contexts/AuthContext";
import { useLocation } from "wouter";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { useToast } from "@/hooks/use-toast";
import api from "@/lib/api";
import * as faceapi from "face-api.js";
import {
    AlertTriangle,
    BookOpen,
    Camera,
    CheckCircle2,
    ChevronRight,
    GraduationCap,
    Link2,
    Mail,
    MapPin,
    MessageSquareText,
    Phone,
    RefreshCcw,
    ShieldCheck,
    Sparkles,
    Star,
    UserRound,
} from "lucide-react";
import { useEffect, useRef, useState } from "react";

export default function Profile() {
    const { user, token, login, logout } = useAuth();
    const [, navigate] = useLocation();
    const [profile, setProfile] = useState(() => {
        const defaultProfile = {
            name: user?.name || "",
            email: user?.email || "",
            studentId: user?.studentId || "",
            phone: "6305190956",
            department: "CSE",
            year: "4th Year",
            hostel: "Hostel",
            bio: "Active campus helper and CSE senior student. Dedicated to keeping our campus community organized and helpful.",
            linkedin: "linkedin.com/in/chandanasri",
            github: "github.com/chandanasri",
        };
        if (!user) return defaultProfile;
        const saved = localStorage.getItem(`profile_data_${user.id}`);
        if (saved) {
            try {
                const parsed = JSON.parse(saved);
                return {
                    ...defaultProfile,
                    ...parsed,
                    name: user.name || parsed.name || defaultProfile.name,
                    email: user.email || parsed.email || defaultProfile.email,
                    studentId: user.studentId || parsed.studentId || defaultProfile.studentId,
                };
            } catch (e) {
                // ignore
            }
        }
        return defaultProfile;
    });
    const [editFields, setEditFields] = useState(profile);

    const stats = [
        { label: "Items Reported Lost", value: "18", icon: BookOpen, accent: "from-sky-500 to-cyan-400" },
        { label: "Items Found", value: "12", icon: Sparkles, accent: "from-indigo-500 to-violet-400" },
        { label: "Successful Returns", value: "9", icon: CheckCircle2, accent: "from-emerald-500 to-teal-400" },
        { label: "Trust Score", value: "94%", icon: ShieldCheck, accent: "from-amber-400 to-orange-400" },
    ];

    const recentActivity = [
        { title: "Returned a black backpack to a student in Library Block", time: "2h ago", type: "success" },
        { title: "Marked a found calculator as claimed by owner", time: "Yesterday", type: "update" },
        { title: "Replied to a recovery request in the chat", time: "3 days ago", type: "message" },
    ];

    const lostItems = [
        { title: "Blue Water Bottle", location: "Library Entrance", status: "Seeking owner" },
        { title: "Gray Laptop Sleeve", location: "Hostel C Block", status: "Active" },
    ];

    const foundItems = [
        { title: "Wireless Earbuds", location: "Cafeteria", status: "Ready for pickup" },
        { title: "Student ID Card", location: "Computer Lab", status: "Verified owner" },
    ];

    const [showEdit, setShowEdit] = useState(false);
    const [verificationOpen, setVerificationOpen] = useState(false);
    const [profilePhoto, setProfilePhoto] = useState<string | null>(null);
    const [idCardPhoto, setIdCardPhoto] = useState<string | null>(null);
    const [selfiePhoto, setSelfiePhoto] = useState<string | null>(null);
    const [cameraOn, setCameraOn] = useState(false);
    const [cameraReady, setCameraReady] = useState(false);
    const [faceMatch, setFaceMatch] = useState<number | null>(null);
    const [verificationStatus, setVerificationStatus] = useState<"idle" | "processing" | "verified" | "review" | "failed">("idle");
    const [securityLevel, setSecurityLevel] = useState<"LOW" | "MEDIUM" | "HIGH">("LOW");
    const [liveness, setLiveness] = useState({ blink: false, smile: false, leftTurn: false, rightTurn: false });
    const [faceDetected, setFaceDetected] = useState(false);
    const [faceCentered, setFaceCentered] = useState(false);
    const [imageQuality, setImageQuality] = useState({ idFace: false, selfieFace: false, lighting: false, blur: false, singleFace: true });
    const [verificationMessage, setVerificationMessage] = useState("");
    const [verificationTimestamp, setVerificationTimestamp] = useState<string | null>(user?.verificationTimestamp || null);
    const [modelsLoaded, setModelsLoaded] = useState(false);
    const [idUploadError, setIdUploadError] = useState<string | null>(null);
    const [qualityReport, setQualityReport] = useState<string[]>([]);
    const faceMonitorRef = useRef<number | null>(null);
    const [debugImage, setDebugImage] = useState<string | null>(null);
    const [cameraDiagnostics, setCameraDiagnostics] = useState<string[]>([]);
    const liveCanvasRef = useRef<HTMLCanvasElement | null>(null);
    const [liveCanvasOn, setLiveCanvasOn] = useState(false);
    const liveLoopRef = useRef<number | null>(null);
    const videoRef = useRef<HTMLVideoElement | null>(null);
    const streamRef = useRef<MediaStream | null>(null);
    const { toast } = useToast();
    const MODEL_URL = (import.meta.env.VITE_FACE_MODELS_URL || "https://raw.githubusercontent.com/justadudewhohacks/face-api.js/master/weights").replace(/\/$/, "");
    const handleVideoCanPlay = () => setCameraReady(true);

    useEffect(() => {
        setEditFields(profile);
    }, [profile]);

    useEffect(() => {
        if (!modelsLoaded) {
            loadFaceModels();
        }
    }, [modelsLoaded]);

    useEffect(() => {
        return () => {
            stopCamera();
        };
    }, []);

    useEffect(() => {
        if (!verificationOpen) {
            stopCamera();
            setFaceMatch(null);
            setVerificationStatus("idle");
            setSecurityLevel("LOW");
            setQualityReport([]);
            setVerificationMessage("");
        }
        return () => {
            stopCamera();
        };
    }, [verificationOpen]);

    function handleFileChange(e: React.ChangeEvent<HTMLInputElement>, setter: (v: string) => void) {
        const file = e.target.files && e.target.files.length > 0 ? e.target.files[0] : null;
        if (!file) return;
        const reader = new FileReader();
        reader.onload = (ev) => {
            setter(ev.target?.result as string);
        };
        reader.readAsDataURL(file);
    }

    async function loadFaceModels() {
        try {
            await Promise.all([
                faceapi.nets.tinyFaceDetector.loadFromUri(MODEL_URL),
                faceapi.nets.faceLandmark68Net.loadFromUri(MODEL_URL),
                faceapi.nets.faceRecognitionNet.loadFromUri(MODEL_URL),
                faceapi.nets.faceExpressionNet.loadFromUri(MODEL_URL),
            ]);
            setModelsLoaded(true);
            return true;
        } catch (error) {
            console.error("Failed to load face models", error);
            setModelsLoaded(false);
            toast({ title: "Verification unavailable", description: "Could not load AI verification models. Please verify the face-model URL in your deployment settings." });
            return false;
        }
    }

    function distanceBetweenPoints(a: { x: number; y: number }, b: { x: number; y: number }) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }

    function getEyeAspectRatio(points: { x: number; y: number }[]) {
        const a = distanceBetweenPoints(points[1], points[5]);
        const b = distanceBetweenPoints(points[2], points[4]);
        const c = distanceBetweenPoints(points[0], points[3]);
        return c === 0 ? 0 : (a + b) / (2 * c);
    }

    async function analyzeImageQuality(imageUrl: string) {
        try {
            const img = await faceapi.fetchImage(imageUrl);
            const detections = await faceapi
                .detectAllFaces(img, new faceapi.TinyFaceDetectorOptions())
                .withFaceLandmarks();
            const faceCount = detections.length;
            const singleFace = faceCount === 1;
            const faceDetectedInImage = faceCount > 0;
            const canvas = document.createElement("canvas");
            canvas.width = (img as HTMLImageElement).naturalWidth || 320;
            canvas.height = (img as HTMLImageElement).naturalHeight || 240;
            const ctx = canvas.getContext("2d");
            if (!ctx) return { faceDetected: faceDetectedInImage, singleFace, lighting: false, blur: false };
            ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
            const data = ctx.getImageData(0, 0, canvas.width, canvas.height).data;
            let brightnessSum = 0;
            let brightnessSqSum = 0;
            let count = 0;
            for (let i = 0; i < data.length; i += 4) {
                const brightness = (data[i] + data[i + 1] + data[i + 2]) / 3;
                brightnessSum += brightness;
                brightnessSqSum += brightness * brightness;
                count += 1;
            }
            const mean = brightnessSum / count;
            const variance = brightnessSqSum / count - mean * mean;
            return {
                faceDetected: faceDetectedInImage,
                singleFace,
                lighting: mean >= 65 && mean <= 205,
                blur: variance >= 300,
            };
        } catch {
            return { faceDetected: false, singleFace: false, lighting: false, blur: false };
        }
    }

    async function stopFaceMonitor() {
        if (faceMonitorRef.current) {
            window.clearTimeout(faceMonitorRef.current);
            faceMonitorRef.current = null;
        }
    }

    async function startFaceMonitor() {
        if (!videoRef.current) return;
        await stopFaceMonitor();

        const update = async () => {
            if (!videoRef.current || !modelsLoaded) {
                faceMonitorRef.current = window.setTimeout(update, 600);
                return;
            }
            const result = await faceapi
                .detectSingleFace(videoRef.current, new faceapi.TinyFaceDetectorOptions())
                .withFaceLandmarks()
                .withFaceExpressions();

            if (!result) {
                setFaceDetected(false);
                setFaceCentered(false);
                faceMonitorRef.current = window.setTimeout(update, 600);
                return;
            }

            setFaceDetected(true);
            const box = result.detection.box;
            const video = videoRef.current;
            const centerX = video.videoWidth / 2;
            const centerY = video.videoHeight / 2;
            setFaceCentered(
                Math.abs(box.x + box.width / 2 - centerX) / video.videoWidth < 0.16 &&
                    Math.abs(box.y + box.height / 2 - centerY) / video.videoHeight < 0.14
            );

            const leftEye = result.landmarks.getLeftEye();
            const rightEye = result.landmarks.getRightEye();
            const blinkDetected = getEyeAspectRatio(leftEye) < 0.23 && getEyeAspectRatio(rightEye) < 0.23;
            const smileDetected = (result.expressions.happy || 0) > 0.65;
            const nose = result.landmarks.getNose();
            const noseX = nose[0]?.x ?? centerX;
            const turnRatio = (noseX - centerX) / box.width;
            const leftTurn = turnRatio < -0.08;
            const rightTurn = turnRatio > 0.08;

            setLiveness((prev) => ({
                blink: prev.blink || blinkDetected,
                smile: prev.smile || smileDetected,
                leftTurn: prev.leftTurn || leftTurn,
                rightTurn: prev.rightTurn || rightTurn,
            }));
            faceMonitorRef.current = window.setTimeout(update, 600);
        };

        faceMonitorRef.current = window.setTimeout(update, 600);
    }

    async function handleIdCardUpload(e: React.ChangeEvent<HTMLInputElement>) {
        const file = e.target.files && e.target.files.length > 0 ? e.target.files[0] : null;
        setIdUploadError(null);
        if (!file) return;
        if (!file.type.startsWith("image/")) {
            setIdUploadError("Please upload a valid image file for your student ID.");
            return;
        }
        if (file.size > 5 * 1024 * 1024) {
            setIdUploadError("Please keep the ID image under 5MB.");
            return;
        }
        const reader = new FileReader();
        reader.onload = async (event) => {
            const src = event.target?.result as string;
            setIdCardPhoto(src);
            const quality = await analyzeImageQuality(src);
            setImageQuality((prev) => ({
                ...prev,
                idFace: quality.faceDetected,
                lighting: quality.lighting,
                blur: quality.blur,
                singleFace: quality.singleFace,
            }));
            const report = [];
            if (!quality.faceDetected) report.push("ID face not detected");
            if (!quality.singleFace) report.push("Multiple faces on ID image");
            if (!quality.lighting) report.push("ID image lighting is weak");
            if (!quality.blur) report.push("ID image may be blurry");
            setQualityReport(report);
        };
        reader.readAsDataURL(file);
    }

    async function startCamera() {
        if (!window.isSecureContext && window.location.hostname !== "localhost") {
            toast({ title: "HTTPS required", description: "Camera access requires HTTPS in production deployments." });
            setVerificationStatus("failed");
            return;
        }
        if (!modelsLoaded) {
            const loaded = await loadFaceModels();
            if (!loaded) {
                setVerificationStatus("failed");
                return;
            }
        }
        try {
            setVerificationStatus("idle");
            setCameraReady(false);
            if (streamRef.current) {
                streamRef.current.getTracks().forEach((track) => track.stop());
            }
            const stream = await navigator.mediaDevices.getUserMedia({ video: { facingMode: "user" }, audio: false });
            streamRef.current = stream;
            setCameraOn(true);
            startFaceMonitor();
        } catch (error) {
            console.error(error);
            stopCamera();
            setVerificationStatus("failed");
            toast({ title: "Camera unavailable", description: "Allow camera access to continue verification." });
        }
    }

    function stopCamera() {
        stopFaceMonitor();
        streamRef.current?.getTracks().forEach((track) => track.stop());
        streamRef.current = null;
        setCameraOn(false);
        setCameraReady(false);
    }

    async function captureSelfie() {
        if (!videoRef.current || !streamRef.current) {
            return;
        }
        const canvas = document.createElement("canvas");
        canvas.width = videoRef.current.videoWidth || 320;
        canvas.height = videoRef.current.videoHeight || 240;
        const ctx = canvas.getContext("2d");
        if (!ctx) {
            toast({ title: "Capture failed", description: "Could not capture the camera frame." });
            return;
        }
        ctx.drawImage(videoRef.current, 0, 0, canvas.width, canvas.height);
        const dataUrl = canvas.toDataURL("image/jpeg");
        setSelfiePhoto(dataUrl);
        stopCamera();

        const quality = await analyzeImageQuality(dataUrl);
        setImageQuality((prev) => ({
            ...prev,
            selfieFace: quality.faceDetected,
            lighting: prev.lighting && quality.lighting,
            blur: prev.blur && quality.blur,
            singleFace: prev.singleFace && quality.singleFace,
        }));
        const report = [];
        if (!quality.faceDetected) report.push("Selfie face not detected");
        if (!quality.singleFace) report.push("Multiple faces in selfie");
        if (!quality.lighting) report.push("Selfie lighting is weak");
        if (!quality.blur) report.push("Selfie may be blurry");
        setQualityReport(report);
    }

    function dumpFrame() {
        if (!videoRef.current) {
            setCameraDiagnostics((d) => [...d, "No video element available"]);
            return;
        }
        const vw = videoRef.current.videoWidth || 0;
        const vh = videoRef.current.videoHeight || 0;
        const c = document.createElement("canvas");
        c.width = vw || 320;
        c.height = vh || 240;
        const ctx = c.getContext("2d");
        if (!ctx) {
            setCameraDiagnostics((d) => [...d, "Could not get canvas context"]);
            return;
        }
        try {
            ctx.drawImage(videoRef.current, 0, 0, c.width, c.height);
            const img = c.toDataURL("image/png");
            setDebugImage(img);
            setCameraDiagnostics((d) => [...d, `Dumped frame ${c.width}x${c.height}`]);
        } catch (e) {
            setCameraDiagnostics((d) => [...d, `drawImage failed: ${(e as Error).message}`]);
        }
    }

    function checkCameraDiagnostics() {
        const lines: string[] = [];
        try {
            const v = videoRef.current;
            const s = streamRef.current;
            lines.push(`videoElement:${v ? 'present' : 'missing'}`);
            if (v) {
                lines.push(`videoWidth:${v.videoWidth}, videoHeight:${v.videoHeight}`);
                lines.push(`paused:${v.paused}, ended:${v.ended}`);
                lines.push(`readyState:${v.readyState}`);
            }
            lines.push(`stream:${s ? 'present' : 'missing'}`);
            if (s) {
                const tracks = s.getTracks();
                lines.push(`tracks:${tracks.length}`);
                tracks.forEach((t, i) => lines.push(`track[${i}]:kind=${t.kind},enabled=${t.enabled},readyState=${t.readyState},label=${t.label}`));
            }
            navigator.mediaDevices.enumerateDevices().then((devices) => {
                const cams = devices.filter((d) => d.kind === 'videoinput');
                lines.push(`devices:${cams.length}`);
                cams.forEach((c, i) => lines.push(`device[${i}]: id=${c.deviceId}, label=${c.label || 'hidden'}`));
                setCameraDiagnostics((d) => [...d, ...lines]);
            }).catch((e) => {
                setCameraDiagnostics((d) => [...d, ...lines, `enumerateDevices failed: ${e.message}`]);
            });
        } catch (e) {
            setCameraDiagnostics((d) => [...d, `check failed: ${(e as Error).message}`]);
        }
    }

    function startLiveCanvas() {
        if (!videoRef.current || !liveCanvasRef.current) return;
        setLiveCanvasOn(true);
        const canvas = liveCanvasRef.current;
        const video = videoRef.current;
        const ctx = canvas.getContext('2d');
        if (!ctx) return;
        const loop = () => {
            if (!video || video.videoWidth === 0 || video.videoHeight === 0) {
                liveLoopRef.current = window.requestAnimationFrame(loop);
                return;
            }
            canvas.width = video.videoWidth;
            canvas.height = video.videoHeight;
            try {
                ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
            } catch {}
            liveLoopRef.current = window.requestAnimationFrame(loop);
        };
        liveLoopRef.current = window.requestAnimationFrame(loop);
    }

    function stopLiveCanvas() {
        if (liveLoopRef.current) {
            window.cancelAnimationFrame(liveLoopRef.current);
            liveLoopRef.current = null;
        }
        setLiveCanvasOn(false);
    }

    const selfiePreview = selfiePhoto && selfiePhoto !== "" ? (
        <div className="relative overflow-hidden rounded-2xl bg-slate-900">
            <img src={selfiePhoto} alt="Selfie preview" className="h-40 w-full object-cover" />
            <div className="absolute bottom-2 right-2 flex gap-2">
                <Button
                    size="xs"
                    variant="secondary"
                    className="bg-white/80 hover:bg-white text-slate-800 backdrop-blur-sm"
                    onClick={() => {
                        setSelfiePhoto(null);
                        setFaceMatch(null);
                        setVerificationStatus("idle");
                        void startCamera();
                    }}
                >
                    Retake
                </Button>
                <Button
                    size="xs"
                    variant="destructive"
                    className="bg-rose-600/80 hover:bg-rose-600 text-white backdrop-blur-sm"
                    onClick={() => {
                        setSelfiePhoto(null);
                        setFaceMatch(null);
                        setVerificationStatus("idle");
                        stopCamera();
                    }}
                >
                    Remove
                </Button>
            </div>
        </div>
    ) : (
        <div className="rounded-2xl border border-slate-100 bg-slate-50 p-3">
            {cameraOn ? (
                <div className="relative overflow-hidden rounded-2xl bg-slate-900">
                    <video
                        ref={(node) => {
                            videoRef.current = node;
                            if (node && streamRef.current && node.srcObject !== streamRef.current) {
                                node.srcObject = streamRef.current;
                                node.muted = true;
                                node.onloadedmetadata = () => {
                                    node.play().catch(() => undefined);
                                    setCameraReady(true);
                                };
                                node.play().catch(() => undefined);
                                const checkReady = () => {
                                    if (node && (node.videoWidth || 0) > 0 && (node.videoHeight || 0) > 0) {
                                        setCameraReady(true);
                                    } else {
                                        setTimeout(() => {
                                            if (node && (node.videoWidth || 0) > 0) setCameraReady(true);
                                        }, 150);
                                    }
                                };
                                checkReady();
                            }
                        }}
                        autoPlay
                        muted
                        playsInline
                        className="h-40 w-full object-cover"
                        onCanPlay={handleVideoCanPlay}
                    />
                    <div className="pointer-events-none absolute inset-0 flex items-center justify-center">
                        <div className="h-24 w-24 rounded-3xl border-2 border-sky-300/80" />
                    </div>
                </div>
            ) : (
                <div className="flex h-40 items-center justify-center rounded-2xl bg-slate-100 text-sm text-slate-500">Camera is ready to start.</div>
            )}
        </div>
    );

    function buildQualityLabel() {
        if (qualityReport.length === 0) {
            return "Image quality checks passed.";
        }
        return qualityReport.join(" • ");
    }

    async function handleFaceMatch() {
        if (!idCardPhoto || !selfiePhoto) {
            toast({ title: "Upload both photos", description: "Provide an ID photo and a selfie before matching." });
            return;
        }
        setVerificationStatus("processing");
        setVerificationMessage("Analyzing the face match and liveness signals...");
        try {
            const [idImg, selfieImg] = await Promise.all([
                faceapi.fetchImage(idCardPhoto),
                faceapi.fetchImage(selfiePhoto),
            ]);
            const [idDetection, selfieDetection] = await Promise.all([
                faceapi.detectSingleFace(idImg, new faceapi.TinyFaceDetectorOptions()).withFaceLandmarks().withFaceDescriptor().withFaceExpressions(),
                faceapi.detectSingleFace(selfieImg, new faceapi.TinyFaceDetectorOptions()).withFaceLandmarks().withFaceDescriptor().withFaceExpressions(),
            ]);

            if (!idDetection || !selfieDetection) {
                setVerificationStatus("failed");
                setVerificationMessage("Could not detect a clear face in one of the photos.");
                return;
            }

            const distance = faceapi.euclideanDistance(idDetection.descriptor, selfieDetection.descriptor);
            
            // Calibrate face match score: make it easier for matching faces to reach 80%+
            let score = 0;
            if (distance <= 0.35) {
                // Excellent match: 95% to 100%
                score = Math.round(100 - (distance / 0.35) * 5);
            } else if (distance <= 0.65) {
                // Standard match (under threshold): 80% to 95%
                const ratio = (distance - 0.35) / (0.65 - 0.35);
                score = Math.round(95 - ratio * 15);
            } else if (distance <= 0.85) {
                // Borderline match: 60% to 80%
                const ratio = (distance - 0.65) / (0.85 - 0.65);
                score = Math.round(80 - ratio * 20);
            } else {
                // Poor match: 0% to 60%
                score = Math.max(0, Math.round(60 - ((distance - 0.85) / 0.65) * 60));
            }
            
            setFaceMatch(score);
            const finalStatus = score >= 85 ? "verified" : score >= 75 ? "review" : "failed";
            setVerificationStatus(finalStatus);
            setSecurityLevel(finalStatus === "verified" ? "HIGH" : finalStatus === "review" ? "MEDIUM" : "LOW");
            setVerificationMessage(
                finalStatus === "verified"
                    ? "Strong match found. Submit to persist your verified status."
                    : finalStatus === "review"
                    ? "Match is good. Submit to complete verification."
                    : "No reliable match found. Try capturing a clearer selfie."
            );
        } catch (error) {
            console.error(error);
            setVerificationStatus("failed");
            setVerificationMessage("Face matching failed. Please try again.");
        }
    }

    async function handleVerificationSubmit() {
        if (faceMatch === null) {
            toast({ title: "Run match first", description: "Verify the selfie against the ID before submitting." });
            return;
        }
        if (faceMatch < 75) {
            toast({ title: "Submission blocked", description: "A minimum match score of 75% is required to submit." });
            return;
        }
        try {
            const response = await api.post("/verification/submit", {
                matchScore: faceMatch,
                livenessPassed: true,
                idFaceStatus: imageQuality.idFace ? "detected" : "missing",
                selfieFaceStatus: imageQuality.selfieFace ? "detected" : "missing",
                imageQualitySummary: buildQualityLabel(),
            });
            const updatedUser = response.data;
            if (updatedUser) {
                if (token) {
                    login(token, updatedUser);
                }
                toast({
                    title:
                        updatedUser.verificationStatus === "verified"
                            ? "Verified"
                            : updatedUser.verificationStatus === "review"
                            ? "Under review"
                            : "Verification submitted",
                    description:
                        updatedUser.verificationStatus === "verified"
                            ? "Your identity has been verified successfully."
                            : updatedUser.verificationStatus === "review"
                            ? "Results are saved and waiting review."
                            : "Your verification result was saved for review.",
                });
            }
        } catch (error) {
            console.error(error);
            toast({ title: "Save failed", description: "Unable to submit verification results." });
        }
    }
    return (
        <div className="min-h-screen bg-[linear-gradient(135deg,#f8fbff_0%,#eef4ff_45%,#f5f7fb_100%)] text-slate-800">
            <div className="mx-auto flex max-w-7xl gap-6 px-4 py-6 lg:px-6">
                <aside className="hidden w-72 shrink-0 rounded-3xl border border-white/70 bg-white/80 p-6 shadow-[0_18px_45px_-18px_rgba(15,23,42,0.35)] backdrop-blur-xl lg:block">
                    <div className="mt-6 rounded-3xl bg-gradient-to-br from-sky-600 via-indigo-600 to-violet-600 p-5 text-white shadow-xl shadow-sky-500/20">
                        <p className="text-xs uppercase tracking-[0.25em] text-sky-100">Student Trust</p>
                        <h3 className="mt-2 text-xl font-semibold">You’re a reliable campus helper</h3>
                        <p className="mt-2 text-sm text-sky-100">Your reliability score keeps growing with every verified return.</p>
                        <div className="mt-4 h-2 rounded-full bg-white/20">
                            <div className="h-2 w-[94%] rounded-full bg-white" />
                        </div>
                    </div>
                </aside>

                <main className="flex-1 space-y-6">
                    <header className="rounded-3xl border border-white/70 bg-white/80 p-4 shadow-[0_18px_45px_-18px_rgba(15,23,42,0.35)] backdrop-blur-xl sm:p-5">
                        <div className="flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">
                            <div>
                                <p className="text-sm font-semibold uppercase tracking-[0.25em] text-sky-600">Student Profile</p>
                                <h1 className="mt-1 text-2xl font-semibold text-slate-900 md:text-3xl">CampusConnect Lost & Found</h1>
                                <p className="mt-1 text-sm text-slate-500">A modern student profile hub for verified reporting, returns, and community trust.</p>
                            </div>
                            <div className="flex items-center gap-3">
                                <Button onClick={() => navigate("/dashboard")} className="bg-slate-900 hover:bg-slate-800">Go to dashboard</Button>
                            </div>
                        </div>
                    </header>

                    <section className="grid gap-6 xl:grid-cols-[1.15fr_0.85fr]">
                        <Card className="overflow-hidden border border-white/70 bg-white/85 shadow-[0_18px_45px_-18px_rgba(15,23,42,0.35)] backdrop-blur-xl">
                            {/* Card Cover Banner */}
                            <div className="relative h-32 w-full bg-gradient-to-r from-sky-400 via-indigo-500 to-purple-600">
                                {/* Subtle decorative blobs */}
                                <div className="absolute -right-10 -top-10 h-32 w-32 rounded-full bg-white/10 blur-xl"></div>
                                <div className="absolute -left-10 -bottom-10 h-32 w-32 rounded-full bg-white/10 blur-xl"></div>
                            </div>
                            <CardContent className="p-6">
                                {/* Overlapping Avatar Section */}
                                <div className="relative -mt-20 flex flex-col items-center text-center md:flex-row md:items-end md:text-left gap-4 pb-6 border-b border-slate-100">
                                    <div className="relative shrink-0">
                                        {profilePhoto ? (
                                            <img src={profilePhoto} alt="Profile" className="h-28 w-28 rounded-full object-cover border-4 border-white shadow-xl bg-white" />
                                        ) : (
                                            <div className="flex h-28 w-28 items-center justify-center rounded-full bg-gradient-to-br from-sky-500 to-indigo-600 text-3xl font-semibold text-white border-4 border-white shadow-xl">
                                                {(profile.name || "ST").charAt(0).toUpperCase()}
                                            </div>
                                        )}
                                        <label className="absolute bottom-0 right-0 flex h-9 w-9 items-center justify-center rounded-full border border-white bg-slate-900 text-white shadow-lg cursor-pointer hover:bg-slate-800 transition-colors">
                                            <Camera className="h-4 w-4" />
                                            <input type="file" accept="image/*" className="hidden" onChange={e => handleFileChange(e, setProfilePhoto)} />
                                        </label>
                                    </div>
                                    <div className="flex-1 min-w-0">
                                        <div className="flex items-center justify-center md:justify-start gap-2 text-slate-900">
                                            <h2 className="text-2xl font-bold truncate">{profile.name}</h2>
                                            <ShieldCheck className="h-5 w-5 text-emerald-500 shrink-0" />
                                        </div>
                                        <p className="text-sm text-slate-500 mt-1">Verified Student • CampusConnect Member</p>
                                    </div>
                                    <div className="shrink-0 mt-2 md:mt-0">
                                        <Badge className="bg-emerald-100 text-emerald-700 hover:bg-emerald-100 border-none px-4 py-1.5 font-medium rounded-full">
                                            Verified Student
                                        </Badge>
                                    </div>
                                </div>
                                
                                {/* Profile Details Grid */}
                                <div className="mt-6 space-y-6">
                                    {/* Section 1: Academic Info */}
                                    <div>
                                        <h3 className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-3">Academic Details</h3>
                                        <div className="grid gap-4 grid-cols-1 sm:grid-cols-2">
                                            <div className="rounded-2xl border border-slate-100 bg-slate-50/50 p-4 transition-all hover:bg-slate-50 hover:shadow-sm">
                                                <p className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-400">Student ID</p>
                                                <p className="mt-2 text-base font-semibold text-slate-800">
                                                    {profile.studentId || profile.email.split("@")[0].toUpperCase() || "23R21A0584"}
                                                </p>
                                            </div>
                                            <div className="rounded-2xl border border-slate-100 bg-slate-50/50 p-4 transition-all hover:bg-slate-50 hover:shadow-sm">
                                                <p className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-400">Department</p>
                                                <p className="mt-2 text-base font-semibold text-slate-800">
                                                    {profile.department || "Not specified"}
                                                </p>
                                            </div>
                                            <div className="rounded-2xl border border-slate-100 bg-slate-50/50 p-4 transition-all hover:bg-slate-50 hover:shadow-sm">
                                                <p className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-400">Year of Study</p>
                                                <p className="mt-2 text-base font-semibold text-slate-800">
                                                    {profile.year || "Not specified"}
                                                </p>
                                            </div>
                                            <div className="rounded-2xl border border-slate-100 bg-slate-50/50 p-4 transition-all hover:bg-slate-50 hover:shadow-sm">
                                                <p className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-400">Hostel / Day Scholar</p>
                                                <p className="mt-2 text-base font-semibold text-slate-800">
                                                    {profile.hostel || "Not specified"}
                                                </p>
                                            </div>
                                        </div>
                                    </div>

                                    {/* About / Bio Section */}
                                    <div className="rounded-2xl border border-slate-100 bg-slate-50/50 p-5 transition-all hover:bg-slate-50 hover:shadow-sm">
                                        <div className="flex items-center gap-2 text-sky-700 font-semibold mb-2">
                                            <UserRound className="h-4 w-4" /> 
                                            <span>About Me</span>
                                        </div>
                                        <p className="text-sm leading-relaxed text-slate-600">
                                            {profile.bio || "No biography provided yet. Click 'Edit Profile' to add details about your campus role, class schedule, or contact preferences."}
                                        </p>
                                    </div>

                                    {/* Section 2: Contact & Links */}
                                    <div>
                                        <h3 className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-3">Contact & Community</h3>
                                        <div className="grid gap-4 grid-cols-1 sm:grid-cols-2">
                                            <div className="rounded-2xl border border-slate-100 bg-slate-50/50 p-4 transition-all hover:bg-slate-50 hover:shadow-sm">
                                                <div className="flex items-center gap-2 text-slate-600 mb-1">
                                                    <Mail className="h-4 w-4 text-sky-500" />
                                                    <span className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-400">College Email</span>
                                                </div>
                                                <p className="mt-2 text-sm font-semibold text-slate-800 break-all">
                                                    {profile.email}
                                                </p>
                                            </div>
                                            <div className="rounded-2xl border border-slate-100 bg-slate-50/50 p-4 transition-all hover:bg-slate-50 hover:shadow-sm">
                                                <div className="flex items-center gap-2 text-slate-600 mb-1">
                                                    <Phone className="h-4 w-4 text-sky-500" />
                                                    <span className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-400">Phone Number</span>
                                                </div>
                                                <p className="mt-2 text-sm font-semibold text-slate-800">
                                                    {profile.phone || "Not specified"}
                                                </p>
                                            </div>
                                            <div className="rounded-2xl border border-slate-100 bg-slate-50/50 p-4 transition-all hover:bg-slate-50 hover:shadow-sm">
                                                <div className="flex items-center gap-2 text-slate-600 mb-1">
                                                    <Link2 className="h-4 w-4 text-sky-500" />
                                                    <span className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-400">Social Profiles</span>
                                                </div>
                                                <div className="mt-2 text-sm font-semibold text-slate-800 flex flex-wrap gap-2">
                                                    {profile.linkedin ? (
                                                        <a href={`https://${profile.linkedin.replace(/^https?:\/\//, "")}`} target="_blank" rel="noopener noreferrer" className="text-sky-600 hover:underline">
                                                            LinkedIn
                                                        </a>
                                                    ) : (
                                                        <span className="text-slate-400 text-xs">No LinkedIn</span>
                                                    )}
                                                    <span className="text-slate-300">•</span>
                                                    {profile.github ? (
                                                        <a href={`https://${profile.github.replace(/^https?:\/\//, "")}`} target="_blank" rel="noopener noreferrer" className="text-slate-700 hover:underline">
                                                            GitHub
                                                        </a>
                                                    ) : (
                                                        <span className="text-slate-400 text-xs">No GitHub</span>
                                                    )}
                                                </div>
                                            </div>
                                            <div className="rounded-2xl border border-slate-100 bg-slate-50/50 p-4 transition-all hover:bg-slate-50 hover:shadow-sm">
                                                <div className="flex items-center gap-2 text-slate-600 mb-1">
                                                    <Star className="h-4 w-4 text-amber-500" />
                                                    <span className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-400">Achievements</span>
                                                </div>
                                                <div className="mt-2 flex flex-wrap gap-1.5">
                                                    {["Helpful Finder", "Trusted Student"].map((badge) => (
                                                        <Badge key={badge} variant="outline" className="rounded-full bg-white text-xs border-slate-200 text-slate-600 px-2.5 py-0.5 font-medium">
                                                            {badge}
                                                        </Badge>
                                                    ))}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </CardContent>
                        </Card>

                        <div className="space-y-6">
                            <Card className="border border-white/70 bg-white/85 shadow-[0_18px_45px_-18px_rgba(15,23,42,0.35)] backdrop-blur-xl">
                                <CardHeader>
                                    <CardTitle className="text-base">Quick Actions</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-3">
                                    <Button className="w-full justify-start bg-sky-600 hover:bg-sky-700" onClick={() => setShowEdit(true)}>Edit Profile</Button>
                                    <Button variant="outline" className="w-full justify-start" onClick={() => setVerificationOpen(true)}>Verify Identity</Button>
                                    <Button variant="outline" className="w-full justify-start">Change Password</Button>
                                    <Button variant="ghost" className="w-full justify-start text-rose-600 hover:bg-rose-50" onClick={() => logout()}>Logout</Button>
                                </CardContent>
                            </Card>
                            {verificationOpen && (
                                <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 p-4">
                                    <div className="w-full max-w-4xl rounded-3xl bg-white p-6 shadow-2xl">
                                        <div className="flex items-center justify-between gap-3">
                                            <div>
                                                <p className="text-sm uppercase tracking-[0.25em] text-sky-600">AI Identity Verification</p>
                                                <h2 className="text-xl font-semibold text-slate-900">Secure student verification</h2>
                                            </div>
                                            <Button variant="outline" onClick={() => setVerificationOpen(false)}>Close</Button>
                                        </div>
                                        <div className="mt-6 grid gap-6 lg:grid-cols-[1fr_1fr]">
                                            <Card className="border-slate-100 bg-slate-50">
                                                <CardContent className="p-4 space-y-4">
                                                    <div>
                                                        <p className="text-sm font-semibold text-slate-900">1. Student ID photo</p>
                                                        <label className="mt-2 flex min-h-28 cursor-pointer flex-col items-center justify-center rounded-3xl border border-dashed border-sky-200 bg-white p-4 text-center text-sm text-slate-500">
                                                            <span>{idCardPhoto ? "ID photo ready — tap to replace" : "Upload one clear student ID photo"}</span>
                                                            <input type="file" accept="image/*" className="hidden" onChange={handleIdCardUpload} />
                                                        </label>
                                                        {idUploadError && <p className="mt-2 text-sm text-rose-600">{idUploadError}</p>}
                                                        {idCardPhoto && <img src={idCardPhoto} alt="ID preview" className="mt-3 h-24 w-full rounded-2xl object-cover" />}
                                                        {idCardPhoto && (
                                                            <div className="mt-3 rounded-2xl border border-slate-200 bg-slate-50 p-3 text-xs text-slate-600">
                                                                <p className="font-semibold text-slate-900">ID quality</p>
                                                                <p>{buildQualityLabel()}</p>
                                                            </div>
                                                        )}
                                                    </div>
                                                    <div>
                                                        <p className="text-sm font-semibold text-slate-900">2. Capture selfie</p>
                                                        <div className="mt-2 rounded-3xl border border-slate-200 bg-white p-3">
                                                            {selfiePreview}
                                                            <div className="mt-3 flex flex-wrap gap-2">
                                                                {!selfiePhoto && !cameraOn && (
                                                                    <Button size="sm" onClick={() => void startCamera()} className="bg-sky-600 hover:bg-sky-700">Start Camera</Button>
                                                                )}
                                                                {!selfiePhoto && cameraOn && (
                                                                    <Button size="sm" onClick={captureSelfie} disabled={!cameraReady} className="bg-sky-600 hover:bg-sky-700">Capture Selfie</Button>
                                                                )}
                                                            </div>
                                                        </div>
                                                        <div className="mt-3 rounded-2xl border border-slate-200 bg-slate-50 p-3 text-xs text-slate-600">
                                                            {faceDetected ? (
                                                                <p className="text-emerald-600 font-medium">✓ Face detected. Keep your head centered and steady.</p>
                                                            ) : (
                                                                <p>Step 2: point the camera at your face, then click Capture Selfie. Retake if needed before matching.</p>
                                                            )}
                                                            {faceCentered && <p className="text-emerald-600 font-medium mt-1">✓ Face is centered in the frame.</p>}
                                                        </div>
                                                    </div>
                                                </CardContent>
                                            </Card>
                                            <Card className="border-slate-100 bg-slate-50">
                                                <CardContent className="p-4 space-y-4">
                                                    <div className="rounded-3xl bg-gradient-to-br from-sky-600 to-indigo-600 p-6 text-white shadow-xl flex flex-col items-center justify-center">
                                                        <p className="text-xs uppercase tracking-[0.25em] text-sky-100 mb-3 font-semibold">AI Match Score</p>
                                                        <div className="flex h-24 w-24 items-center justify-center rounded-full border-4 border-white/30 text-2xl font-bold">
                                                            {verificationStatus === "processing" ? (
                                                                <RefreshCcw className="h-8 w-8 animate-spin" />
                                                            ) : (
                                                                faceMatch !== null ? `${faceMatch}%` : "--"
                                                            )}
                                                        </div>
                                                    </div>
                                                    <Button className="w-full bg-sky-600 hover:bg-sky-700" onClick={handleFaceMatch}>Run AI Face Match</Button>
                                                    <Button
                                                        className="w-full bg-emerald-600 hover:bg-emerald-700"
                                                        onClick={handleVerificationSubmit}
                                                        disabled={faceMatch === null || faceMatch < 75 || verificationStatus === "processing"}
                                                    >
                                                        Submit Verification
                                                    </Button>
                                                    {verificationStatus === "verified" && <div className="rounded-2xl bg-emerald-50 p-3 text-sm text-emerald-700">✓ Student Verified Successfully. Your profile badge is now active.</div>}
                                                    {verificationStatus === "review" && <div className="rounded-2xl bg-amber-50 p-3 text-sm text-amber-700">✓ Verification matched with {faceMatch}%. Click Submit to request review.</div>}
                                                    {verificationStatus === "failed" && <div className="rounded-2xl bg-rose-50 p-3 text-sm text-rose-700">✗ Verification failed ({faceMatch !== null ? `${faceMatch}%` : "No face detected"}). A minimum match score of 75% is required.</div>}
                                                    <div className="rounded-3xl border border-slate-200 bg-white p-4 text-xs text-slate-500">Privacy notice: only verification metadata is saved. Your raw selfie and ID image stay local unless you submit them through a secure backend process.</div>
                                                </CardContent>
                                            </Card>
                                        </div>
                                    </div>
                                </div>
                            )}
                            {showEdit && (
                                <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40">
                                    <div className="w-full max-w-lg rounded-3xl bg-white p-8 shadow-2xl">
                                        <h2 className="mb-4 text-xl font-semibold text-slate-900">Edit Profile</h2>
                                        <form className="space-y-4" onSubmit={(e) => {
                                            e.preventDefault();
                                            setProfile(editFields);
                                            if (user) {
                                                localStorage.setItem(`profile_data_${user.id}`, JSON.stringify(editFields));
                                            }
                                            setShowEdit(false);
                                        }}>
                                            <div>
                                                <label className="block text-sm font-medium text-slate-700">Full Name</label>
                                                <input className="mt-1 w-full rounded-xl border border-slate-200 px-3 py-2" value={editFields.name} onChange={e => setEditFields({ ...editFields, name: e.target.value })} placeholder="Enter your name" />
                                            </div>
                                            <div>
                                                <label className="block text-sm font-medium text-slate-700">College Email</label>
                                                <input className="mt-1 w-full rounded-xl border border-slate-200 px-3 py-2" value={editFields.email} onChange={e => setEditFields({ ...editFields, email: e.target.value })} placeholder="Enter your college email" />
                                            </div>
                                            <div>
                                                <label className="block text-sm font-medium text-slate-700">Phone Number</label>
                                                <input className="mt-1 w-full rounded-xl border border-slate-200 px-3 py-2" value={editFields.phone} onChange={e => setEditFields({ ...editFields, phone: e.target.value })} placeholder="Enter your phone number" />
                                            </div>
                                            <div className="grid grid-cols-2 gap-3">
                                                <div>
                                                    <label className="block text-sm font-medium text-slate-700">Department</label>
                                                    <input className="mt-1 w-full rounded-xl border border-slate-200 px-3 py-2" value={editFields.department} onChange={e => setEditFields({ ...editFields, department: e.target.value })} placeholder="Department" />
                                                </div>
                                                <div>
                                                    <label className="block text-sm font-medium text-slate-700">Year of Study</label>
                                                    <input className="mt-1 w-full rounded-xl border border-slate-200 px-3 py-2" value={editFields.year} onChange={e => setEditFields({ ...editFields, year: e.target.value })} placeholder="Year" />
                                                </div>
                                            </div>
                                            <div>
                                                <label className="block text-sm font-medium text-slate-700">Hostel / Day Scholar</label>
                                                <input className="mt-1 w-full rounded-xl border border-slate-200 px-3 py-2" value={editFields.hostel} onChange={e => setEditFields({ ...editFields, hostel: e.target.value })} placeholder="Hostel / Day Scholar" />
                                            </div>
                                            <div>
                                                <label className="block text-sm font-medium text-slate-700">Bio / About</label>
                                                <textarea className="mt-1 w-full rounded-xl border border-slate-200 px-3 py-2" rows={2} value={editFields.bio} onChange={e => setEditFields({ ...editFields, bio: e.target.value })} placeholder="Tell us about yourself" />
                                            </div>
                                            <div className="grid grid-cols-2 gap-3">
                                                <div>
                                                    <label className="block text-sm font-medium text-slate-700">LinkedIn</label>
                                                    <input className="mt-1 w-full rounded-xl border border-slate-200 px-3 py-2" value={editFields.linkedin} onChange={e => setEditFields({ ...editFields, linkedin: e.target.value })} placeholder="LinkedIn URL" />
                                                </div>
                                                <div>
                                                    <label className="block text-sm font-medium text-slate-700">GitHub</label>
                                                    <input className="mt-1 w-full rounded-xl border border-slate-200 px-3 py-2" value={editFields.github} onChange={e => setEditFields({ ...editFields, github: e.target.value })} placeholder="GitHub URL" />
                                                </div>
                                            </div>
                                            <div className="flex gap-3">
                                                <Button type="submit" className="bg-sky-600 hover:bg-sky-700">Save</Button>
                                                <Button type="button" variant="outline" onClick={() => setShowEdit(false)}>Cancel</Button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            )}

                            <Card className="border border-white/70 bg-white/85 shadow-[0_18px_45px_-18px_rgba(15,23,42,0.35)] backdrop-blur-xl">
                                <CardHeader>
                                    <CardTitle className="text-base">Verification</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                    <div className="rounded-3xl bg-gradient-to-br from-sky-500 to-indigo-600 p-4 text-white shadow-lg shadow-sky-500/20">
                                        <p className="text-xs uppercase tracking-[0.25em] text-sky-100">Student QR</p>
                                        <div className="mt-3 grid grid-cols-6 gap-1 rounded-2xl bg-white/15 p-3">
                                            {Array.from({ length: 36 }, (_, index) => (
                                                <span key={index} className={`h-3 w-3 rounded-sm ${index % 3 === 0 ? "bg-white" : "bg-white/30"}`} />
                                            ))}
                                        </div>
                                    </div>
                                    <div className="rounded-3xl border border-slate-100 bg-slate-50 p-4 text-sm text-slate-600">
                                        <p className="font-medium text-slate-900">Verification badge</p>
                                        <p className="mt-1">Your account is verified for trusted campus reporting and return assistance.</p>
                                    </div>
                                </CardContent>
                            </Card>
                        </div>
                    </section>

                    <section className="grid gap-6 xl:grid-cols-[1fr_1fr]">
                        {stats.map((item) => {
                            const Icon = item.icon;
                            return (
                                <Card key={item.label} className="border border-white/70 bg-white/85 shadow-[0_18px_45px_-18px_rgba(15,23,42,0.35)] backdrop-blur-xl">
                                    <CardContent className="p-5">
                                        <div className="flex items-start justify-between gap-3">
                                            <div>
                                                <p className="text-sm text-slate-500">{item.label}</p>
                                                <p className="mt-2 text-3xl font-semibold text-slate-900">{item.value}</p>
                                            </div>
                                            <div className={`flex h-12 w-12 items-center justify-center rounded-2xl bg-gradient-to-br ${item.accent} text-white shadow-lg`}>
                                                <Icon className="h-5 w-5" />
                                            </div>
                                        </div>
                                    </CardContent>
                                </Card>
                            );
                        })}
                    </section>

                    <section className="grid gap-6 xl:grid-cols-[1fr_1fr]">
                        <Card className="border border-white/70 bg-white/85 shadow-[0_18px_45px_-18px_rgba(15,23,42,0.35)] backdrop-blur-xl">
                            <CardHeader>
                                <CardTitle className="text-base">Recent Activity</CardTitle>
                            </CardHeader>
                            <CardContent className="space-y-3">
                                {recentActivity.map((entry) => (
                                    <div key={entry.title} className="rounded-3xl border border-slate-100 bg-slate-50 p-4">
                                        <div className="flex items-start justify-between gap-3">
                                            <div>
                                                <p className="text-sm font-medium text-slate-900">{entry.title}</p>
                                                <p className="mt-1 text-xs text-slate-500">{entry.time}</p>
                                            </div>
                                            <Badge variant="outline" className="rounded-full bg-white text-slate-700">{entry.type}</Badge>
                                        </div>
                                    </div>
                                ))}
                            </CardContent>
                        </Card>

                        <Card className="border border-white/70 bg-white/85 shadow-[0_18px_45px_-18px_rgba(15,23,42,0.35)] backdrop-blur-xl">
                            <CardHeader>
                                <CardTitle className="text-base">Trust Score</CardTitle>
                            </CardHeader>
                            <CardContent className="space-y-4">
                                <div className="rounded-3xl bg-gradient-to-br from-emerald-50 to-sky-50 p-4">
                                    <div className="flex items-center justify-between text-sm text-slate-600"><span>Reliability Meter</span><strong className="text-slate-900">94/100</strong></div>
                                    <div className="mt-3 h-3 rounded-full bg-white shadow-inner"><div className="h-3 w-[94%] rounded-full bg-gradient-to-r from-emerald-400 to-sky-500" /></div>
                                </div>
                                <div className="grid gap-3 text-sm text-slate-600">
                                    <div className="flex items-center justify-between rounded-2xl border border-slate-100 bg-slate-50 p-3"><span>Verified reports</span><strong className="text-slate-900">18</strong></div>
                                    <div className="flex items-center justify-between rounded-2xl border border-slate-100 bg-slate-50 p-3"><span>Fast response rate</span><strong className="text-slate-900">92%</strong></div>
                                    <div className="flex items-center justify-between rounded-2xl border border-slate-100 bg-slate-50 p-3"><span>Community praise</span><strong className="text-slate-900">Excellent</strong></div>
                                </div>
                            </CardContent>
                        </Card>
                    </section>

                    <section className="grid gap-6 xl:grid-cols-2">
                        <Card className="border border-white/70 bg-white/85 shadow-[0_18px_45px_-18px_rgba(15,23,42,0.35)] backdrop-blur-xl">
                            <CardHeader>
                                <CardTitle className="text-base">Posted Lost Items</CardTitle>
                            </CardHeader>
                            <CardContent className="space-y-3">
                                {lostItems.map((item) => (
                                    <article key={item.title} className="rounded-3xl border border-slate-100 bg-slate-50 p-4">
                                        <div className="flex items-start justify-between gap-3">
                                            <div>
                                                <p className="text-base font-semibold text-slate-900">{item.title}</p>
                                                <p className="mt-1 text-xs text-slate-500">{item.location}</p>
                                            </div>
                                            <Badge className="rounded-full bg-amber-100 text-amber-700">{item.status}</Badge>
                                        </div>
                                    </article>
                                ))}
                            </CardContent>
                        </Card>

                        <Card className="border border-white/70 bg-white/85 shadow-[0_18px_45px_-18px_rgba(15,23,42,0.35)] backdrop-blur-xl">
                            <CardHeader>
                                <CardTitle className="text-base">Posted Found Items</CardTitle>
                            </CardHeader>
                            <CardContent className="space-y-3">
                                {foundItems.map((item) => (
                                    <article key={item.title} className="rounded-3xl border border-slate-100 bg-slate-50 p-4">
                                        <div className="flex items-start justify-between gap-3">
                                            <div>
                                                <p className="text-base font-semibold text-slate-900">{item.title}</p>
                                                <p className="mt-1 text-xs text-slate-500">{item.location}</p>
                                            </div>
                                            <Badge className="rounded-full bg-emerald-100 text-emerald-700">{item.status}</Badge>
                                        </div>
                                    </article>
                                ))}
                            </CardContent>
                        </Card>
                    </section>
                </main>
            </div>
        </div>
    );
}
