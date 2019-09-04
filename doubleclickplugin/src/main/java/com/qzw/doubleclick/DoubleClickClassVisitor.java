package com.qzw.doubleclick;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * @auther: qizewei
 * @date: 2019-09-03
 * @e-mail: qizewei@koolearn.com
 * @description：
 */
public class DoubleClickClassVisitor extends ClassVisitor implements Opcodes {

    private String mClassName;

    public DoubleClickClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.mClassName = name;
        super.visit(version, access, name, signature, superName, interfaces);
        this.visitField(ACC_PRIVATE, "lastClickTime", "J", null, null);
        this.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "FAST_CLICK_DELAY_TIME", "I", null, new Integer(1000));
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        //此处可以根据 name 继续筛选匹配
       if ("onClick".equals(name)) {
           System.out.println("------------ [ChangeMethod] name:" + name + ",desc=" + desc + ",access=" + access);
           mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
                @Override
                protected void onMethodEnter() {
                    super.onMethodEnter();
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, mClassName, "lastClickTime", "J");
                    mv.visitInsn(LSUB);
                    mv.visitLdcInsn(new Long(1000L));
                    mv.visitInsn(LCMP);
                    Label l1 = new Label();
                    mv.visitJumpInsn(IFGE, l1);
                    Label l2 = new Label();
                    mv.visitLabel(l2);
                    mv.visitLineNumber(19, l2);
                    mv.visitInsn(RETURN);
                    mv.visitLabel(l1);
                    mv.visitLineNumber(21, l1);
                    mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                    mv.visitFieldInsn(PUTFIELD, mClassName, "lastClickTime", "J");
                }
            };
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
