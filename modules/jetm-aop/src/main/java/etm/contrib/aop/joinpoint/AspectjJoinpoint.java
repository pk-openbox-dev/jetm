/*
 *
 * Copyright (c) void.fm
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name void.fm nor the names of its contributors may be
 * used to endorse or promote products derived from this software without specific
 * prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package etm.contrib.aop.joinpoint;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.codehaus.aspectwerkz.joinpoint.impl.MethodSignatureImpl;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;

/**
 * AspectJ joinpoint.
 * 
 * @author jenglisch
 * @version $Revision: 298 $ $Date: 2009-07-10 16:08:16 +0200 (Fr, 10 Jul 2009) $
 * @since 1.2.4 
 */
public class AspectjJoinpoint extends AbstractJoinPoint {

  private ProceedingJoinPoint joinPoint;
  
  public AspectjJoinpoint(ProceedingJoinPoint aJoinPoint) {
    joinPoint = aJoinPoint;
  }

  public String calculateName() {
    Object target = joinPoint.getTarget();
    if (joinPoint instanceof MethodInvocationProceedingJoinPoint) {
      Signature signature = ((MethodInvocationProceedingJoinPoint) joinPoint).getSignature();
      if (signature instanceof MethodSignatureImpl) {
        String method = ((MethodSignatureImpl) signature).getName();
        return calculateName(target.getClass(), method);              
      }      
    } 
    return calculateShortName(target.getClass());
  }

  public Object proceed() throws Throwable {
    return joinPoint.proceed();
  }

}
