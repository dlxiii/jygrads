%
% Demonstrates usage of the "grads" interface class in Matlab. Notice that it 
% requires the JVM. 
%
% Arlindo.daSilva@opengrads.org
% This file is placed in the public domain.
%

% Starts GrADS
% ------------
  ga = grads('grads -bl',0);

% Ok, this one should exist
% -------------------------
  fprintf(1,'Opening a CTL file:\n');
  fh = ga.open('data/model');
  disp fh.title;
  fprintf(1,'              File Id: %d\n', fh.fid);
  fprintf(1,'         Dataset type: %s\n', fh.type);
  fprintf(1,'    No. of Time steps: %d\n', fh.nt);
  fprintf(1,'    No. of Longitudes: %d\n', fh.nx);
  fprintf(1,'    No. of  Latitudes: %d\n', fh.ny);
  fprintf(1,'    No. of     Levels: %d\n', fh.nz);
  fprintf(1,'      Variable  names: %s\n',fh.vars);
  fprintf(1,'      Variable levels: %d %d %d %d %d %d %d %d\n',fh.var_levs);
  fprintf(1,'      Variable titles: '); disp(fh.var_titles);
  disp ' ';
  fprintf(1,'>>> OK <<< open GRIB file\n');

% Ok, sdfopen should work as well
% -------------------------------
  fprintf(1,'Opening a NetCDF file:\n');
  fh = ga.open('data/model.nc');
  disp fh.title;
  fprintf(1,'              File Id: %d\n', fh.fid);
  fprintf(1,'         Dataset type: %s\n', fh.type);
  fprintf(1,'    No. of Time steps: %d\n', fh.nt);
  fprintf(1,'    No. of Longitudes: %d\n', fh.nx);
  fprintf(1,'    No. of  Latitudes: %d\n', fh.ny);
  fprintf(1,'    No. of     Levels: %d\n', fh.nz);
  fprintf(1,'      Variable  names: %s\n',fh.vars);
  fprintf(1,'      Variable levels: %d %d %d %d %d %d %d %d\n',fh.var_levs);
  fprintf(1,'      Variable titles: '); disp(fh.var_titles);
  disp ' ';
  fprintf(1,'>>> OK <<< open NetCDF file\n');

% Next, check the query method
% ----------------------------
  qh = ga.query('dims');
  fprintf(1,'Current dimensional state:\n');
  fprintf(1,'  X is %s,   Lon = (%f,%f),  X = (%d,%d)\n',qh.x_state,qh.lon,qh.xx);
  fprintf(1,'  Y is %s,   Lat = (%f,%f),  Y = (%d,%d)\n',qh.y_state,qh.lat,qh.yy);
  fprintf(1,'  Z is %s,   Lev = (%f,%f),  Z = (%d,%d)\n',qh.z_state,qh.lev,qh.zz);
  fprintf(1,'  T is %s,  Time = (%s),  T = (%d,%d)\n',qh.t_state,qh.time,qh.tt);
  disp ' '
  fprintf(1,'>>> OK <<< query dimensions');
  disp ' '

  qh = ga.query('file');
  fprintf(1,'Current file state:\n ');
  fprintf(1,'         Title: %s\n', char(qh.title));
  fprintf(1,'       File Id: %d\n', qh.fid);
  fprintf(1,'   Description: %s\n', char(qh.desc));
  fprintf(1,'        Binary: %s\n', char(qh.bin));
  disp ' '

  fprintf(1,'>>> OK <<< query file\n');

% Test output capture, 2 modes: line and words
% --------------------------------------------
  ga.cmd('q config');

    fprintf(1,'--------------------------------------------------------------\n');
    fprintf(1,'            Captured GrADS output: Line interface\n');
    fprintf(1,'--------------------------------------------------------------\n');
    nLines = ga.rline();
    for i = 1:nLines
        fprintf(1,'%s\n',ga.rline(i));
    end
    fprintf(1,'                          ---------\n');

    disp ' '
    fprintf(1,'--------------------------------------------------------------\n');
    fprintf(1,'            Captured GrADS output: Word interface\n');
    fprintf(1,'--------------------------------------------------------------\n');
    for i = 1:nLines
        for j = 1:20   % 20 is an over estimate, but i is OK
            fprintf(1,'%s ',ga.rword(i,j));
        end
        disp ' ';
    end
    fprintf(1,'                          ---------\n');
    disp ' ';
    fprintf(1,'>>> OK <<< rline()/rword() completes\n');

%  Retrieve variable from GrADS and plot it
% -----------------------------------------
  [ts,g] = ga.expr('ts');
  fprintf(1,'Ts in Kelvins: min=%f,  max=%f\n', min(min(ts)), max(max(ts)));
  pcolor(g.lon,g.lat,ts);
  shading('interp');
  title('Surface Temperature');
  xlabel('Longitude'); 
  ylabel('Latitude'); 
  disp ' '

disp 'All done.';
