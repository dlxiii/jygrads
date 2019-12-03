classdef grads < handle
%  The class GRADS implements an interface to the Grid Analysis and
%  Display System (GrADS) by means of bi-directional pipes.  It allows
%  you to start GrADS, send commands to it and to retrieve the text
%  and binary output produced by the *grads* application in response
%  to such commands. This class is a simple wrapper around JyGrADS, a
%  Java/Jython based interface to GrADS. In addition, it also requires
%  the GrADS binaries to have been installed on your system.
%  http://wiki.opengrads.org/index.php?title=JyGrADS:_Java_and_Matlab_interface_to_GrADS_through_Jython#The_MATLAB_Interface

   properties
      ja  % the GrADS Java object
   end

   methods (Static=true)
      function s = hashmap2struct(jh)
      % HASHMAP2STRUCT Converts a Java HashMap to a Matlab structure
         k = jh.keySet().toArray();
         v = jh.values().toArray();
         for i=1:jh.size
           if ( isa(v(i),'org.python.core.PyTuple') | ...
                isa(v(i),'org.python.core.PyList') ) 
             s.(k(i)) = char(v(i));
           else
             s.(k(i)) = v(i);
           end
         end
      end
   end

   methods

      function self = grads(Bin,Echo)
         if nargin == 2
           self.ja = org.opengrads.core.Grads(Bin,Echo);
         else
           self.ja = org.opengrads.core.Grads(Bin);
         end
      end
      function cmd(self,str,quiet)
         if nargin == 3
           self.ja.cmd(str,quiet);
         else
           self.ja.cmd(str);
         end
      end
      function flush(self)
         self.ja.flush();
      end
      function line = rline(self,i)
         if nargin == 2
           line = char(self.ja.rline(i));
         else
           line = self.ja.rline();
         end
      end
      function word = rword(self,i,j)
         word = char(self.ja.rword(i,j));
      end
      function qh = query(self,str,quiet)
         if nargin == 3
           jh = self.ja.query(str,quiet);
         else
           jh = self.ja.query(str);
         end
         qh = grads.hashmap2struct(jh);
      end
      function fh = open(self,fname,ftype,quiet)
         if nargin == 4
           jh = self.ja.open(fname,ftype,quiet);
         elseif nargin == 3
           jh = self.ja.open(fname,ftype);
         else
           jh = self.ja.open(fname);
         end
         fh = grads.hashmap2struct(jh);
      end
      function ch = coords(self)
         jh = self.ja.coords();
         ch = grads.hashmap2struct(jh);
         ch.denv = grads.hashmap2struct(ch.denv);
      end
      function [x,varargout] = expr(self,expr)
         x = self.ja.eval(expr);
         ch = self.coords();
         shape = ch.shape'; 
         n = length(shape);
         x = double(reshape(x,shape(n:-1:1)))'; % Fortran/Matlab indexxing
         varargout(1) = {ch}; % coordinate variables (grid)
      end
      function display(self)
         self.cmd('q config')
      end
   end
   
end
